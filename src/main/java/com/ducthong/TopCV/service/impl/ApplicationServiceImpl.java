package com.ducthong.TopCV.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ducthong.TopCV.constant.meta.MetaConstant;
import com.ducthong.TopCV.domain.dto.application.ApplicationRequestDTO;
import com.ducthong.TopCV.domain.dto.application.ApplicationResponseDTO;
import com.ducthong.TopCV.domain.dto.application.AppliedCandidateResponseDTO;
import com.ducthong.TopCV.domain.dto.application.StatisticApplicationResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.SortingDTO;
import com.ducthong.TopCV.domain.entity.Application;
import com.ducthong.TopCV.domain.entity.CV;
import com.ducthong.TopCV.domain.entity.Company;
import com.ducthong.TopCV.domain.entity.Job;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.enums.ApplicationStatus;
import com.ducthong.TopCV.domain.mapper.ApplicationMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.ApplicationRepository;
import com.ducthong.TopCV.repository.CvRepository;
import com.ducthong.TopCV.repository.JobRepository;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.service.ApplicationService;
import com.ducthong.TopCV.service.CompanyService;
import com.ducthong.TopCV.service.CvService;
import com.ducthong.TopCV.service.JobService;
import com.ducthong.TopCV.utility.GetRoleUtil;
import com.ducthong.TopCV.utility.TimeUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    // Repository
    private final JobRepository jobRepo;
    private final ApplicationRepository applicationRepo;
    private final CvRepository cvRepo;
    // Service
    private final CvService cvService;
    private final CompanyService companyService;
    private final JobService jobService;
    // Mapper
    private final ApplicationMapper applicationMapper;
    // Variant
    private final String CV_LINK = "http://localhost:3000/cv-profile/";

    @Override
    public ApplicationResponseDTO addApplication(Integer accountId, ApplicationRequestDTO requestDTO) {
        // Candidate
        Candidate candidate = GetRoleUtil.getCandidate(accountId);
        // Job
        Optional<Job> jobFind = jobRepo.findById(requestDTO.jobId());
        if (jobFind.isEmpty()) throw new AppException("This job is not existed");
        Job job = jobFind.get();

        for (Application item : candidate.getApplications())
            if (item.getJob().getId() == requestDTO.jobId())
                throw new AppException("You have applied for this job before");
        // CV
        // CV cv = cvService.isCvAccess(requestDTO.cvId(), accountId);
        String cvLink = CV_LINK + candidate.getCvProfile().getId();
        if (requestDTO.cvId() != null && !requestDTO.cvId().equals("")) {
            Optional<CV> cv = cvRepo.findById(requestDTO.cvId());
            if (cv.isEmpty()) throw new AppException("CV này không tồn tại");
            cvLink = cv.get().getCvLink();
        }

        Application applicationNew = Application.builder()
                // .cvLink(cv.getCvLink())
                .cvLink(cvLink)
                .status(ApplicationStatus.NEW)
                .applicationTime(TimeUtil.getDateTimeNow())
                .lastUpdated(null)
                .statusChangeTime(null)
                .candidate(candidate)
                .job(job)
                .build();
        Application applicationSave = applicationRepo.save(applicationNew);

        return applicationMapper.toApplicationResponseDto(applicationSave);
    }

    @Override
    public StatisticApplicationResponseDTO statisticCvByCompany(Integer accountId) {
        Company company = companyService.isVerifyCompanyByAccountId(accountId);

        int numerOfCv = applicationRepo.statisticByStatus(company.getId(), null);
        int numberOfApplyCv = applicationRepo.statisticByStatus(company.getId(), ApplicationStatus.NEW);
        int numberOfOpenContactCv = applicationRepo.statisticByStatus(company.getId(), ApplicationStatus.CONTACT_ALLOW);
        int numberOfInterviewCv =
                applicationRepo.statisticByStatus(company.getId(), ApplicationStatus.INTERVIEW_APPOINTMENT);
        int numberOfFollowCv = applicationRepo.statisticByStatus(company.getId(), ApplicationStatus.FOLLOWING);

        return StatisticApplicationResponseDTO.builder()
                .numberOfCv(numerOfCv)
                .numberOfApplyCv(numberOfApplyCv)
                .numberOfOpenContactCv(numberOfOpenContactCv)
                .numberOfInterviewCv(numberOfInterviewCv)
                .numberOfFollowCv(numberOfFollowCv)
                .build();
    }

    @Override
    public MetaResponse<MetaResponseDTO, List<AppliedCandidateResponseDTO>> getAppliedCandidateByJob(
            Integer jobId, Integer accountId, MetaRequestDTO requestDTO) {
        Job job = jobService.isVerifiedJob(jobId, accountId);

        Sort sort = requestDTO.sortDir().equals(MetaConstant.Sorting.DEFAULT_DIRECTION)
                ? Sort.by(requestDTO.sortField()).ascending()
                : Sort.by(requestDTO.sortField()).descending();
        Pageable pageable = PageRequest.of(requestDTO.currentPage(), requestDTO.pageSize(), sort);
        Page<Application> page = applicationRepo.getApplicationByJobId(job.getId(), pageable);
        List<AppliedCandidateResponseDTO> li = page.getContent().stream()
                .map(item -> applicationMapper.toAppliedCandidateResponseDto(item))
                .toList();
        return MetaResponse.successfulResponse(
                "Get applied candidate by job successful",
                MetaResponseDTO.builder()
                        .totalItems((int) page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .currentPage(requestDTO.currentPage())
                        .pageSize(requestDTO.pageSize())
                        .sorting(SortingDTO.builder()
                                .sortField(requestDTO.sortField())
                                .sortDir(requestDTO.sortDir())
                                .build())
                        .build(),
                li);
    }
}
