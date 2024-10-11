package com.ducthong.TopCV.service.impl;

import com.ducthong.TopCV.domain.dto.application.ApplicationRequestDTO;
import com.ducthong.TopCV.domain.dto.application.ApplicationResponseDTO;
import com.ducthong.TopCV.domain.dto.application.StatisticApplicationResponseDTO;
import com.ducthong.TopCV.domain.entity.Application;
import com.ducthong.TopCV.domain.entity.CV;
import com.ducthong.TopCV.domain.entity.Company;
import com.ducthong.TopCV.domain.entity.Job;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.enums.ApplicationStatus;
import com.ducthong.TopCV.domain.mapper.ApplicationMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.ApplicationRepository;
import com.ducthong.TopCV.repository.JobRepository;
import com.ducthong.TopCV.service.ApplicationService;
import com.ducthong.TopCV.service.CompanyService;
import com.ducthong.TopCV.service.CvService;
import com.ducthong.TopCV.utility.GetRoleUtil;
import com.ducthong.TopCV.utility.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    // Repository
    private final JobRepository jobRepo;
    private final ApplicationRepository applicationRepo;
    // Service
    private final CvService cvService;
    private final CompanyService companyService;
    // Mapper
    private final ApplicationMapper applicationMapper;
    @Override
    public ApplicationResponseDTO addApplication(Integer accountId, ApplicationRequestDTO requestDTO) {
        // Candidate
        Candidate candidate = GetRoleUtil.getCandidate(accountId);
        // Job
        Optional<Job> jobFind = jobRepo.findById(requestDTO.jobId());
        if (jobFind.isEmpty()) throw new AppException("This job is not existed");
        Job job = jobFind.get();

        for (Application item : candidate.getApplications())
            if (item.getJob().getId() == requestDTO.jobId()) throw new AppException("You have applied for this job before");
        // CV
        CV cv = cvService.isCvAccess(requestDTO.cvId(), accountId);

        Application applicationNew = Application.builder()
                .cvLink(cv.getCvLink())
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
        int numberOfInterviewCv = applicationRepo.statisticByStatus(company.getId(), ApplicationStatus.INTERVIEW_APPOINTMENT);
        int numberOfFollowCv = applicationRepo.statisticByStatus(company.getId(), ApplicationStatus.FOLLOWING);

        return StatisticApplicationResponseDTO.builder()
                .numberOfCv(numerOfCv)
                .numberOfApplyCv(numberOfApplyCv)
                .numberOfOpenContactCv(numberOfOpenContactCv)
                .numberOfInterviewCv(numberOfInterviewCv)
                .numberOfFollowCv(numberOfFollowCv)
                .build();
    }
}
