package com.ducthong.TopCV.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ducthong.TopCV.domain.dto.cloudinary.CloudinaryResponseDTO;
import com.ducthong.TopCV.domain.dto.cv.CvRequestDTO;
import com.ducthong.TopCV.domain.dto.cv.CvResponseDTO;
import com.ducthong.TopCV.domain.dto.cv.UpdCvRequestDTO;
import com.ducthong.TopCV.domain.entity.CV;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.mapper.CvMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.CvRepository;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.CloudinaryService;
import com.ducthong.TopCV.service.CvService;
import com.ducthong.TopCV.utility.GetRoleUtil;
import com.ducthong.TopCV.utility.TimeUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CvServiceImpl implements CvService {
    // Repository
    private final CvRepository cvRepo;
    // Service
    private final CloudinaryService cloudinaryService;
    // Mapper
    private final CvMapper cvMapper;
    // Variant
    @Value("${cloudinary.folder.cv}")
    private String CV_FOLDER;

    public CV isCvAccess(String cvId, Integer accoutId) {
        Optional<CV> cvFind = cvRepo.findById(cvId);
        if (cvFind.isEmpty()) throw new AppException("This cv is not existed");
        CV cv = cvFind.get();
        if (cv.getCandidate().getId() != accoutId) throw new AppException("No access to this cv");

        return cv;
    }

    @Override
    public List<CvResponseDTO> getListCvByAccountId(Integer candidateId) {
        Candidate candidate = GetRoleUtil.getCandidate(candidateId);

        if (candidate.getCvs().isEmpty()) throw new AppException("This account has not CV");

        List<CvResponseDTO> cvList = candidate.getCvs().stream()
                .map(item -> cvMapper.toCvResponseDto(item))
                .toList();

        return cvList;
    }

    @Override
    @Transactional
    public CvResponseDTO addCv(Integer candidateId, CvRequestDTO requestDTO) throws IOException {
        Candidate candidate = GetRoleUtil.getCandidate(candidateId);

        CV cvNew = cvMapper.cvRequestDtoToEntity(requestDTO);
        CloudinaryResponseDTO cvUpload = cloudinaryService.uploadFileBase64_v2(requestDTO.cvFile(), CV_FOLDER);
        cvNew.setName(cvUpload.name());
        cvNew.setCvLink(cvUpload.url());
        cvNew.setCvPublicId(cvUpload.public_id());
        cvNew.setCandidate(candidate);

        try {
            CV cvSave = cvRepo.save(cvNew);
            return cvMapper.toCvResponseDto(cvSave);
        } catch (Exception e) {
            throw new AppException("Add a CV is fail");
        }
    }

    @Override
    public CvResponseDTO updateCv(Integer accountId, UpdCvRequestDTO requestDTO) throws IOException {
        CV cvTemp = isCvAccess(requestDTO.id(), accountId);

        if (!requestDTO.cvName().isEmpty()) cvTemp.setName(requestDTO.cvName());
        if (!requestDTO.cvFile().isEmpty()) {
            CloudinaryResponseDTO cvUpload = cloudinaryService.uploadFileBase64_v2(requestDTO.cvFile(), CV_FOLDER);
            cvTemp.setCvLink(cvUpload.url());
            cvTemp.setCvPublicId(cvUpload.public_id());
        }
        if (requestDTO.isPublic() != null) cvTemp.setIsPublic(requestDTO.isPublic());
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
