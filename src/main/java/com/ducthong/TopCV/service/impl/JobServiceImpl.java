package com.ducthong.TopCV.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ducthong.TopCV.domain.dto.job.EmployerJobResponseDTO;
import com.ducthong.TopCV.repository.ApplicationRepository;
import com.ducthong.TopCV.utility.AuthUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.ducthong.TopCV.constant.meta.MetaConstant;
import com.ducthong.TopCV.domain.dto.job.DetailJobResponseDTO;
import com.ducthong.TopCV.domain.dto.job.JobRequestDTO;
import com.ducthong.TopCV.domain.dto.job.JobResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.SortingDTO;
import com.ducthong.TopCV.domain.entity.Image;
import com.ducthong.TopCV.domain.entity.Industry;
import com.ducthong.TopCV.domain.entity.IndustryJob;
import com.ducthong.TopCV.domain.entity.Job;
import com.ducthong.TopCV.domain.entity.account.Employer;
import com.ducthong.TopCV.domain.entity.address.JobAddress;
import com.ducthong.TopCV.domain.mapper.AddressMapper;
import com.ducthong.TopCV.domain.mapper.JobMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.IndustryRepository;
import com.ducthong.TopCV.repository.JobRepository;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.service.ImageService;
import com.ducthong.TopCV.service.JobService;
import com.ducthong.TopCV.utility.GetRoleUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableTransactionManagement
public class JobServiceImpl implements JobService {
    // Repository
    private final IndustryRepository industryRepo;
    private final JobRepository jobRepo;
    private final ApplicationRepository applicationRepo;
    // Service
    private final ImageService imageService;
    // Mapper
    private final JobMapper jobMapper;
    private final AddressMapper addressMapper;
    // Varient
    @Value("${cloudinary.folder.job}")
    private String JOB_FOLDER;

    @Override
    @Transactional
    public DetailJobResponseDTO getDetailJob(Integer jobId) {
        Optional<Job> findJob = jobRepo.findById(jobId);
        if (findJob.isEmpty()) throw new AppException("This job is not existed");

        Boolean isApply = false;
        if (AuthUtil.getRequestedUser() != null &&
                applicationRepo.checkAccountAppliedJob(jobId, AuthUtil.getRequestedUser().getId()))
            isApply = true;

        return jobMapper.toDetailJobResponseDto(findJob.get(), isApply);
    }

    @Override
    public MetaResponse<MetaResponseDTO, List<EmployerJobResponseDTO>> getListJobByCompany(MetaRequestDTO metaRequestDTO, Integer accountId) {
        Employer employer = GetRoleUtil.getEmployer(accountId);
        if (employer.getCompany().getId() == null) throw new AppException("This account doesn't register company");

        Sort sort = metaRequestDTO.sortDir().equals(MetaConstant.Sorting.DEFAULT_DIRECTION)
                ? Sort.by(metaRequestDTO.sortField()).ascending()
                : Sort.by(metaRequestDTO.sortField()).descending();
        Pageable pageable = PageRequest.of(metaRequestDTO.currentPage(), metaRequestDTO.pageSize(), sort);
        Page<Job> page = jobRepo.getListJobByCompany(pageable, employer.getCompany().getId());
        if (page.getContent().isEmpty()) throw new AppException("List job is empty");
        List<EmployerJobResponseDTO> li = page.getContent().stream()
                .map(temp -> jobMapper.toEmployerJobResponseDto(temp))
                .toList();
        return MetaResponse.successfulResponse(
                "Get list job success",
                MetaResponseDTO.builder()
                        .totalItems((int) page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .currentPage(metaRequestDTO.currentPage())
                        .pageSize(metaRequestDTO.pageSize())
                        .sorting(SortingDTO.builder()
                                .sortField(metaRequestDTO.sortField())
                                .sortDir(metaRequestDTO.sortDir())
                                .build())
                        .build(),
                li);
    }

    @Override
    public MetaResponse<MetaResponseDTO, List<JobResponseDTO>> getListJobSpecification(MetaRequestDTO metaRequestDTO) {
        List<Job> jobList = jobRepo.findAll();

        List<JobResponseDTO> res = jobList.stream().map(
                item -> jobMapper.toJobResponseDto(item)
        ).toList();
        return MetaResponse.successfulResponse(
                "Get list job success",
                MetaResponseDTO.builder()
                        .totalItems(null)
                        .totalPages(null)
                        .currentPage(metaRequestDTO.currentPage())
                        .pageSize(metaRequestDTO.pageSize())
                        .sorting(null)
                        .build(),
                res);
    }

    @Override
    public MetaResponse<MetaResponseDTO, List<JobResponseDTO>> getListJob(MetaRequestDTO metaRequestDTO) {
        Sort sort = metaRequestDTO.sortDir().equals(MetaConstant.Sorting.DEFAULT_DIRECTION)
                ? Sort.by(metaRequestDTO.sortField()).ascending()
                : Sort.by(metaRequestDTO.sortField()).descending();
        Pageable pageable = PageRequest.of(metaRequestDTO.currentPage(), metaRequestDTO.pageSize(), sort);
        Page<Job> page = jobRepo.findAll(pageable);
        if (page.getContent().isEmpty()) throw new AppException("List car is empty");
        List<JobResponseDTO> li = page.getContent().stream()
                .map(temp -> jobMapper.toJobResponseDto(temp))
                .toList();
        return MetaResponse.successfulResponse(
                "Get list job success",
                MetaResponseDTO.builder()
                        .totalItems((int) page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .currentPage(metaRequestDTO.currentPage())
                        .pageSize(metaRequestDTO.pageSize())
                        .sorting(SortingDTO.builder()
                                .sortField(metaRequestDTO.sortField())
                                .sortDir(metaRequestDTO.sortDir())
                                .build())
                        .build(),
                li);
    }

    @Override
    @Transactional
    public DetailJobResponseDTO addJob(JobRequestDTO requestDTO, Integer accountId) throws IOException {
        Employer employer = GetRoleUtil.getEmployer(accountId);

        Job newJob = jobMapper.jobRequestDtoToJobEntity(requestDTO);
        // Industry
        List<IndustryJob> industryJobs = new ArrayList<>();

        Optional<Industry> mainIndustryFind = industryRepo.findById(requestDTO.mainIndustry());
        if (mainIndustryFind.isEmpty()) throw new AppException("This main industry is not existed");
        IndustryJob mainIndustryJob = IndustryJob.builder()
                .industry(mainIndustryFind.get())
                .job(newJob)
                .isMain(true)
                .build();
        industryJobs.add(mainIndustryJob);
        requestDTO.subIndustries().forEach(
                item -> {
                    Optional<Industry> temp = industryRepo.findById(item);
                    if (temp.isEmpty()) throw new AppException("This industry is not existed");
                    IndustryJob industryJob = IndustryJob.builder()
                            .industry(temp.get())
                            .job(newJob)
                            .isMain(false)
                            .build();
                    industryJobs.add(industryJob);
                }
        );
        industryJobs.add(mainIndustryJob);
        newJob.setIndustries(industryJobs);
        // Company
        newJob.setCompany(employer.getCompany());
        // Job Image
        List<Image> imageList= new ArrayList<>();
        if (!requestDTO.imageList().isEmpty()){
            imageList = imageService.uploadListBase64Image(requestDTO.imageList(), JOB_FOLDER);
        }
        newJob.setImageList(imageList);
        // Address
        List<JobAddress> jobAddressList = requestDTO.addressList().stream()
                .map(item -> {
                    JobAddress temp = addressMapper.toJobAddress(item);
                    temp.setJob(newJob);
                    return temp;
                })
                .toList();
        newJob.setAddresses(jobAddressList);
        try {
            Job saveJob = jobRepo.save(newJob);
            return jobMapper.toDetailJobResponseDto(saveJob, false);
        } catch (Exception e) {
            throw new AppException("Add new job is failed");
        }
    }
}
