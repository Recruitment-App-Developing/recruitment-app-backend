package com.ducthong.TopCV.extract_data.service.impl;

import com.ducthong.TopCV.domain.dto.cv.CvRequestDTO;
import com.ducthong.TopCV.domain.entity.CV;
import com.ducthong.TopCV.domain.entity.CvProfile.Education;
import com.ducthong.TopCV.domain.entity.CvProfile.Experience;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.extract_data.dto.*;
import com.ducthong.TopCV.extract_data.entity.Award;
import com.ducthong.TopCV.extract_data.entity.AwardAudit;
import com.ducthong.TopCV.extract_data.entity.CvInfor;
import com.ducthong.TopCV.extract_data.repository.*;
import com.ducthong.TopCV.extract_data.service.CvInforService;
import com.ducthong.TopCV.extract_data.service.CvTrackingService;
import com.ducthong.TopCV.extract_data.service.ExtractDataService;
import com.ducthong.TopCV.repository.CvRepository;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.utility.AuthUtil;
import com.ducthong.TopCV.utility.Common;
import com.ducthong.TopCV.utility.TimeUtil;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CvInforServiceImpl implements CvInforService {
    private final EntityManager entityManager;
    private final CvInforRepository cvInforRepo;
    private final CvRepository cvRepo;
    private final CvInforRepositoryCustom cvInforRepoCustom;
    private final CvInforAuditRepository cvInforAuditRepo;
    private final AwardAuditRepo awardAuditRepo;
    private final AwardRepository awardRepo;
    private final ExtractDataService extractDataService;
    private final CvTrackingService cvTrackingService;
    private final String CV_NAME_PREFIX = "cv_";
    private final String CV_NAME_SUFFIX = ".pdf";
    private final String CV_FOLDER = "src/main/resources/cvFile/";

    @Override
    public List<CvInforItem> getList() {
        return cvInforRepoCustom.getListCvInfor();
    }

    @Override
    @Transactional
    public DetailCvInforDTO getDetail(String cvInforId) {
        Optional<CvInfor> cvInforOpt = cvInforRepo.findById(cvInforId);
        if (cvInforOpt.isEmpty()) throw new AppException("Không tìm thấy thông tin của CV");

        CvInfor cvInfor = cvInforOpt.get();
        Optional<CV> cvOpt = cvRepo.findById(cvInfor.getCvId());
        if (cvOpt.isEmpty()) throw new AppException("Không tìm thấy CV");

        DetailCvInforDTO response = new DetailCvInforDTO();
        // Cv
        response.setCvId(cvOpt.get().getId());
        response.setCvName(cvOpt.get().getName());
        // General infor
        response.setCvInforId(cvInforId);
        response.setFullName(cvInfor.getFullName());
        response.setEmail(cvInfor.getEmail());
        response.setDob(TimeUtil.toStringDate(cvInfor.getDateOfBirth()));
        response.setPhone(cvInfor.getPhone());
        response.setAddress(cvInfor.getAddress());
        response.setApplicationPosition(cvInfor.getApplicationPostion());
        // Award
        List<DetailCvInforDTO.Award> awards = new ArrayList<>();
        cvInfor.getAwards().forEach(award -> {
            DetailCvInforDTO.Award temp = new DetailCvInforDTO.Award();
            temp.setId(award.getId());
            temp.setName(award.getName());
            temp.setTimeStr(award.getTime());

            awards.add(temp);
        });
        response.setAwards(awards);
        // Education
        List<DetailCvInforDTO.Education> educations = new ArrayList<>();
        cvInfor.getEducations().forEach(education -> {
            DetailCvInforDTO.Education temp = new DetailCvInforDTO.Education();
            temp.setId(education.getId());
            temp.setSchoolName(education.getSchoolName());
            temp.setTimeStr(education.getTimeStr());
            temp.setDetail(education.getDetail());

            educations.add(temp);
        });
        response.setEducations(educations);
        // Experience
        List<DetailCvInforDTO.Experience> experiences = new ArrayList<>();
        cvInfor.getExperiences().forEach(experience -> {
            DetailCvInforDTO.Experience temp = new DetailCvInforDTO.Experience();
            temp.setId(experience.getId());
            temp.setCompanyName(experience.getCompanyName());
            temp.setPosition(experience.getPosition());
            temp.setTimeStr(experience.getTimeStr());
            temp.setDetail(experience.getDetail());

            experiences.add(temp);
        });
        response.setExperiences(experiences);
        return response;
    }

    @Override
    public Response uploadCvFiles(EmployerUploadCvRequest request) {
        List<CvRequestDTO> cvRequestDTOList = new ArrayList<>();

        List<String> cvIdList = new ArrayList<>();
        for (int i = 0; i < request.getFiles().size(); i++) {
            cvIdList.add(Common.generateId());
        }

        for (int i = 0; i < request.getFiles().size(); i++) {
            String cvId = cvIdList.get(i);

            Path cvFileFolder = Paths.get(CV_FOLDER);
            Path filePath = cvFileFolder.resolve(CV_NAME_PREFIX + cvId + CV_NAME_SUFFIX);
            try {
                Files.copy(request.getFiles().get(i).getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                CV cvNew = new CV();
                cvNew.setName(request.getNames().get(i));
                cvNew.setWhenCreated(TimeUtil.getDateTimeNow());
                cvNew.setLastUpdated(null);
                cvNew.setIsPublic(false);
                cvNew.setId(cvId);
                cvNew.setCreator(AuthUtil.getRequestedUser().getId());
                log.info("[CV_INFOR_SERVICE] Save cv file to disk: {} - {}", cvId, request.getNames().get(i));
                CV cvSave = cvRepo.save(cvNew);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        cvTrackingService.registerSession(request.getSessionId(), request.getFiles().size());
        for (int i = 0; i < request.getFiles().size(); i++) {
            String cvId = cvIdList.get(i);
            log.info("[CV_INFOR_SERVICE] Extract data cv file: {} - {}", cvId, request.getNames().get(i));
            extractDataService.extractDataWithWebSocket(request.getFiles().get(i), cvId, request.getSessionId());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                log.error("Error sleep");
            }
        }
        return Response.successfulResponse("Upload thành công");
    }

    @Override
    @Transactional
    public Response update(DetailCvInforDTO request) {
        Optional<CvInfor> cvInforOpt = cvInforRepo.findById(request.getCvInforId());
        if (cvInforOpt.isEmpty()) {
            throw new AppException("Không tìm thấy CV này!");
        }
        // Update CV name
        Optional<CV> cvOpt = cvRepo.findById(request.getCvId());
        if (cvOpt.isEmpty()) {
            throw new AppException("Không tìm thấy CV này!");
        }
        CvInfor cvInfor = cvInforOpt.get();
        CV cv = cvOpt.get();

        BeanUtils.copyProperties(request, cvInfor);
        // Create maps for efficient lookup
        Map<String, Award> existingAwardMap = cvInfor.getAwards().stream()
                .filter(a -> a.getId() != null)
                .collect(Collectors.toMap(Award::getId, Function.identity()));

        Set<String> newAwardIds = request.getAwards().stream()
                .map(DetailCvInforDTO.Award::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<Award> awardsToDelete = existingAwardMap.values().stream()
                .filter(award -> !newAwardIds.contains(award.getId()))
                .collect(Collectors.toList());

        List<String> awardIdsToDelete = awardsToDelete.stream()
                .map(Award::getId)
                .collect(Collectors.toList());

        List<Award> managedAwardsToDelete = awardRepo.findAllById(awardIdsToDelete);
        Map<String, Award> awardMap = new HashMap<>();
        for (Award managedAward : managedAwardsToDelete) {
            Award newAward = new Award();
            BeanUtils.copyProperties(managedAward, newAward);
            awardMap.put(managedAward.getId(), newAward);
            cvInfor.getAwards().remove(managedAward);
            awardRepo.delete(managedAward);
        }

        for (DetailCvInforDTO.Award dto : request.getAwards()) {
            Award award;

            if (dto.getId() != null && existingAwardMap.containsKey(dto.getId())) {
                // Update existing award
                award = existingAwardMap.get(dto.getId());
            } else {
                // Create new award
                award = new Award();
                award.setCvInfor(cvInfor);
                cvInfor.getAwards().add(award);
            }

            // Set award properties
            award.setName(dto.getName());
            award.setTime(dto.getTimeStr());
        }

        Map<String, Experience> oldExpMap = cvInfor.getExperiences().stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(Experience::getId, Function.identity()));

        List<String> newExpIds = request.getExperiences().stream()
                .map(DetailCvInforDTO.Experience::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        cvInfor.getExperiences().removeIf(e -> e.getId() != null && !newExpIds.contains(e.getId()));

        for (DetailCvInforDTO.Experience dto : request.getExperiences()) {
            Experience experience = (dto.getId() != null && oldExpMap.containsKey(dto.getId()))
                    ? oldExpMap.get(dto.getId())
                    : new Experience();

            experience.setId(dto.getId());
            experience.setCompanyName(dto.getCompanyName());
            experience.setPosition(dto.getPosition());
            experience.setTimeStr(dto.getTimeStr());
            experience.setCvInfor(cvInfor);

            if (!cvInfor.getExperiences().contains(experience)) {
                cvInfor.getExperiences().add(experience);
            }
        }

        Map<String, Education> oldEduMap = cvInfor.getEducations().stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(Education::getId, Function.identity()));

        List<String> newEduIds = request.getEducations().stream()
                .map(DetailCvInforDTO.Education::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        cvInfor.getEducations().removeIf(e -> e.getId() != null && !newEduIds.contains(e.getId()));

        for (DetailCvInforDTO.Education dto : request.getEducations()) {
            Education education = (dto.getId() != null && oldEduMap.containsKey(dto.getId()))
                    ? oldEduMap.get(dto.getId())
                    : new Education();

            education.setId(dto.getId());
            education.setSchoolName(dto.getSchoolName());
            education.setMainIndustry(dto.getIndustry());
            education.setTimeStr(dto.getTimeStr());
            education.setCvInfor(cvInfor);

            if (!cvInfor.getEducations().contains(education)) {
                cvInfor.getEducations().add(education);
            }
        }

        cvInforRepo.saveAndFlush(cvInfor);

        List<Number> revisions = AuditReaderFactory.get(entityManager)
                .getRevisions(CvInfor.class, cvInfor.getId());

        Number latestRev = revisions.get(revisions.size() - 1);
        List<AwardAudit> awardAudits = awardAuditRepo.getlist(awardIdsToDelete, latestRev.intValue()+1);
        for (AwardAudit awardAudit : awardAudits) {
            if (awardMap.containsKey(awardAudit.getId())) {
                Award old = awardMap.get(awardAudit.getId());
                awardAudit.setName(old.getName());
                awardAudit.setTime(old.getTime());

                awardAuditRepo.save(awardAudit);
            }
        }
        cv.setName(request.getCvName());
        cvRepo.save(cv);
        return Response.successfulResponse("Cập nhật thành công");
    }

    @Override
    public Response getCvInforAuditById(String id, String type) {
        List result = new ArrayList();

        switch (type) {
            case "award": {
                result = getAwardAuditById(id);
                break;
            }
            default: {
                throw new AppException("");
            }
        }

        return Response.successfulResponse("Lấy danh sách thành công", result);
    }

    @Override
    public Response getCvInforAuditByCvInfoId(String cvInfoId, String type) {
        List result = new ArrayList();

        switch (type) {
            case "award": {
                result = getAwardAuditByCvInfoId(cvInfoId);
                break;
            }
            default: {
                throw new AppException("");
            }
        }

        return Response.successfulResponse("Lấy danh sách thành công", result);
    }

    private List getAwardAuditByCvInfoId(String cvInfoId) {
        List<AwardAuditDTO> queryResults = cvInforAuditRepo.getListAwardAuditByCvInfoId(cvInfoId);
        List<AwardAuditResponse> results = new ArrayList<>();
        for (AwardAuditDTO item : queryResults) {
            AwardAuditResponse temp = new AwardAuditResponse();
            BeanUtils.copyProperties(item, temp);
            temp.setRevtstmp(Common.convertTimestampToDate(item.getRevtstmp()));

            results.add(temp);
        }
        return results;
    }

    private List<AwardAuditResponse> getAwardAuditById(String id) {
        List<AwardAuditDTO> queryResults = cvInforAuditRepo.getListAwardAuditById(id);
        List<AwardAuditResponse> results = new ArrayList<>();
        for (AwardAuditDTO item : queryResults) {
            AwardAuditResponse temp = new AwardAuditResponse();
            BeanUtils.copyProperties(item, temp);
            temp.setRevtstmp(Common.convertTimestampToDate(item.getRevtstmp()));

            results.add(temp);
        }
        return results;
    }
}
