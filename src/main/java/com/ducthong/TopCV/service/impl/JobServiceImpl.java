package com.ducthong.TopCV.service.impl;

import com.ducthong.TopCV.domain.dto.job.DetailJobResponseDTO;
import com.ducthong.TopCV.domain.dto.job.JobRequestDTO;
import com.ducthong.TopCV.domain.entity.Image;
import com.ducthong.TopCV.domain.entity.Industry;
import com.ducthong.TopCV.domain.entity.Job;
import com.ducthong.TopCV.domain.entity.account.Employer;
import com.ducthong.TopCV.domain.entity.address.Address;
import com.ducthong.TopCV.domain.entity.address.CompanyAddress;
import com.ducthong.TopCV.domain.mapper.JobMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.AddressRepository;
import com.ducthong.TopCV.repository.IndustryRepository;
import com.ducthong.TopCV.repository.JobRepository;
import com.ducthong.TopCV.service.ImageService;
import com.ducthong.TopCV.service.JobService;
import com.ducthong.TopCV.utility.GetRoleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final AddressRepository addressRepo;
    // Service
    private final ImageService imageService;
    // Mapper
    private final JobMapper jobMapper;
    // Varient
    @Value("${cloudinary.folder.job}")
    private String JOB_FOLDER;
    @Override
    @Transactional
    public DetailJobResponseDTO addJob(JobRequestDTO requestDTO, Integer accountId) throws IOException {
        Employer employer = GetRoleUtil.getEmployer(accountId);

        Job newJob = jobMapper.jobRequestDtoToJobEntity(requestDTO);
        // Industry
        Optional<Industry> industry = industryRepo.findById(requestDTO.industryId());
        if (industry.isEmpty()) throw new AppException("This industry is not existed");
        newJob.setIndustry(industry.get());
        // Company
        newJob.setCompany(employer.getCompany());
        // Job Image
        List<Image> imageList = imageService.uploadListBase64Image(requestDTO.imageList(), JOB_FOLDER);
        newJob.setImageList(imageList);
        // Address
        List<CompanyAddress> addresses = new ArrayList<>();
        requestDTO.addressIdList().forEach(
            item -> {
                Optional<Address> address = addressRepo.findById(item);
                if (address.isEmpty()) throw new AppException("This address is not exsited");
                if (address.get() instanceof CompanyAddress) {
                    addresses.add((CompanyAddress) address.get());
                } else {
                    throw new AppException("This address is not a CompanyAddress");
                }
            }
        );
        newJob.setAddresses(addresses);

        try {
            Job saveJob = jobRepo.save(newJob);
            return jobMapper.toDetailJobResponseDto(saveJob);
        } catch (Exception e) {
            throw new AppException("Add new job is failed");
        }
    }
}
