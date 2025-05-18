package com.ducthong.TopCV.service.impl;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import com.ducthong.TopCV.constant.Constants;
import com.ducthong.TopCV.constant.meta.MetaConstant;
import com.ducthong.TopCV.domain.dto.job.JobResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.SortingDTO;
import com.ducthong.TopCV.extract_data.service.ExtractDataService;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.utility.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ducthong.TopCV.domain.dto.cloudinary.CloudinaryResponseDTO;
import com.ducthong.TopCV.domain.dto.cv.CvRequestDTO;
import com.ducthong.TopCV.domain.dto.cv.CvResponseDTO;
import com.ducthong.TopCV.domain.dto.cv.UpdCvRequestDTO;
import com.ducthong.TopCV.domain.entity.Application;
import com.ducthong.TopCV.domain.entity.CV;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.mapper.CvMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.CvRepository;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.CloudinaryService;
import com.ducthong.TopCV.service.CvService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

@Slf4j
@Service
@RequiredArgsConstructor
public class CvServiceImpl implements CvService {
    // Repository
    private final CvRepository cvRepo;
    // Service
    private final CloudinaryService cloudinaryService;
    private final ExtractDataService extractDataService;
    // Mapper
    private final CvMapper cvMapper;
    // Variant
//    @Value("${cloudinary.folder.cv}")
//    private String CV_FOLDER;
    private final String CV_NAME_PREFIX = "cv_";
    private final String CV_NAME_SUFFIX = ".pdf";
    private final String CV_FOLDER = "src/main/resources/cvFile/";

    public CV isCvAccess(String cvId, Integer accoutId) {
        Optional<CV> cvFind = cvRepo.findById(cvId);
        if (cvFind.isEmpty()) throw new AppException("CV này không tồn tại");
        CV cv = cvFind.get();
        if (cv.getCandidate().getId() != accoutId) throw new AppException("Không có quyền truy cập CV này");

        return cv;
    }

    @Override
    public InputStreamResource getCvById(String cvId) {
//        CV cv = isCvAccess(cvId, AuthUtil.getRequestedUser().getId());
        // todo: sửa get one cv
        CV cv = cvRepo.findById(cvId).get();
        String path = CV_FOLDER + CV_NAME_PREFIX + cv.getId() + CV_NAME_SUFFIX;
        File file = new File(path);
        try {
            InputStream inputStream = new FileInputStream(file);
            return new InputStreamResource(inputStream);
        } catch (IOException e) {
            log.error("[ERROR_GET_CV] error reading file");
            throw new AppException("Lấy CV bị lỗi");
        }
    }

    @Override
    public MetaResponse<MetaResponseDTO, List<CvResponseDTO>> getListCvByAccountId(MetaRequestDTO metaRequestDTO) {
        Sort sort = Sort.by("when_created").ascending();

        Pageable pageable = PageRequest.of(metaRequestDTO.currentPage(), 4, sort);
        Page<CV> page = cvRepo.findCvByAccountId(AuthUtil.getRequestedUser().getId(), pageable);
        List<CV> cvList = page.getContent();

        if (ListUtils.isNullOrEmpty(cvList)) throw new AppException("Danh sách CV trống");

        List<CvResponseDTO> result = cvList.stream()
                .map(item -> toCvResponseDto(item))
                .toList();
        MetaResponse<MetaResponseDTO, List<CvResponseDTO>> res = MetaResponse.successfulResponse(
                "Lấy danh sách CV thành công",
                MetaResponseDTO.builder()
                        .totalItems((int) page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .currentPage(metaRequestDTO.currentPage())
                        .pageSize(4)
                        .sorting(SortingDTO.builder()
                                .sortField("when_created")
                                .sortDir(metaRequestDTO.sortDir())
                                .build())
                        .build(),
                result);
        return res;
    }

    private CvResponseDTO toCvResponseDto(CV entity) {
        String path = CV_FOLDER + "/" + CV_NAME_PREFIX + entity.getId() + CV_NAME_SUFFIX;

        String base64 = "";
        base64 = Common.extractFirstPageAsBase64(path);

        return CvResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cvLink(base64)
                .cvType(entity.getCvType().name())
                .whenCreated(TimeUtil.toStringDateTime(entity.getWhenCreated()))
                .lastUpdated(TimeUtil.toStringDateTime(entity.getLastUpdated()))
                .build();
    }

    @Override
    @Transactional
    public CvResponseDTO addCv(Integer candidateId, CvRequestDTO requestDTO) throws IOException {
        Path cvFileFolder = Paths.get(CV_FOLDER);
        MultipartFile file = requestDTO.cvFile();

        if (file == null || file.isEmpty()) {
            throw new AppException("File CV không hợp lệ");
        }
        Candidate candidate = GetRoleUtil.getCandidate(candidateId);

        for (CV item : candidate.getCvs())
            if (Objects.equals(item.getName(), requestDTO.name())) throw new AppException("Tên CV này đã tồn tại");
        // Save file
        String cvId = Common.generateId();

        extractDataService.extractData(requestDTO.cvFile(), cvId);

        Path filePath = cvFileFolder.resolve(CV_NAME_PREFIX + cvId + CV_NAME_SUFFIX);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        // Save infor
        CV cvNew = cvMapper.cvRequestDtoToEntity(requestDTO);
        cvNew.setId(cvId);
        cvNew.setCandidate(candidate);

        try {
            CV cvSave = cvRepo.save(cvNew);
            return cvMapper.toCvResponseDto(cvSave);
        } catch (Exception e) {
            throw new AppException("Add a CV is fail");
        }
    }

    @Override
    @Transactional
    public CvResponseDTO updateCv(Integer accountId, UpdCvRequestDTO requestDTO) throws IOException {
        CV cvTemp = isCvAccess(requestDTO.id(), accountId);

        cvTemp.setName(requestDTO.cvName());
        cvTemp.setLastUpdated(TimeUtil.getDateTimeNow());
        CV cvNew = cvRepo.save(cvTemp);

        return cvMapper.toCvResponseDto(cvNew);
    }

    @Override
    public Response<String> deleteCv(Integer accountId, String cvId) {
        CV cv = isCvAccess(cvId, accountId);
        try {
            cvRepo.deleteById(cv.getId());
            return Response.successfulResponse("Delete a CV sucessfull");
        } catch (Exception e) {
            throw new AppException("Delete a CV fail");
        }
    }
}
