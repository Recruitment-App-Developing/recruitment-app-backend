package com.ducthong.TopCV.service.impl;

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
import com.ducthong.TopCV.domain.entity.address.Address;
import com.ducthong.TopCV.domain.entity.address.CompanyAddress;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@EnableTransactionManagement
public class JobServiceImpl implements JobService {
    // Repository
    private final IndustryRepository industryRepo;
    private final JobRepository jobRepo;
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

        return jobMapper.toDetailJobResponseDto(findJob.get());
    }
    @Override
    public MetaResponse<MetaResponseDTO, List<JobResponseDTO>> getListJob(MetaRequestDTO metaRequestDTO, String name) {
        Sort sort = metaRequestDTO.sortDir().equals(MetaConstant.Sorting.DEFAULT_DIRECTION)
                ? Sort.by(metaRequestDTO.sortField()).ascending()
                : Sort.by(metaRequestDTO.sortField()).descending();
        Pageable pageable = PageRequest.of(metaRequestDTO.currentPage(), metaRequestDTO.pageSize(), sort);
        Page<Job> page = jobRepo.getListJob(pageable, name);
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
        List<IndustryJob> industryJobs = requestDTO.industryList().stream().map(
            item -> {
                Optional<Industry> temp = industryRepo.findById(item);
                if (temp.isEmpty()) throw new AppException("This industry is not existed");
                Boolean isMain = requestDTO.industryList().indexOf(item) == 0;
                return IndustryJob.builder()
                        .industry(temp.get())
                        .job(newJob)
                        .isMain(isMain)
                        .build();
            }
        ).toList();
        newJob.setIndustries(industryJobs);
        // Company
        newJob.setCompany(employer.getCompany());
        // Job Image
        List<Image> imageList = imageService.uploadListBase64Image(requestDTO.imageList(), JOB_FOLDER);
        newJob.setImageList(imageList);
        // Address
        List<JobAddress> jobAddressList = requestDTO.addressList().stream().map(
                item -> {
                    JobAddress temp = addressMapper.toJobAddress(item);
                    temp.setJob(newJob);
                    return temp;
                }
        ).toList();
        newJob.setAddresses(jobAddressList);
        try {
            Job saveJob = jobRepo.save(newJob);
            return jobMapper.toDetailJobResponseDto(saveJob);
        } catch (Exception e) {
            throw new AppException("Add new job is failed");
        }
    }
}
