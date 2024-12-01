package com.ducthong.TopCV.domain.mapper.impl;

import java.util.*;

import org.springframework.stereotype.Component;

import com.ducthong.TopCV.domain.dto.image.ImageResponseDTO;
import com.ducthong.TopCV.domain.dto.job.*;
import com.ducthong.TopCV.domain.dto.job.job_address.JobAddressResponseDTO;
import com.ducthong.TopCV.domain.entity.*;
import com.ducthong.TopCV.domain.entity.address.Address;
import com.ducthong.TopCV.domain.entity.address.JobAddress;
import com.ducthong.TopCV.domain.enums.ApplicationMethod;
import com.ducthong.TopCV.domain.enums.Gender;
import com.ducthong.TopCV.domain.enums.JobPosition;
import com.ducthong.TopCV.domain.enums.WorkMethod;
import com.ducthong.TopCV.domain.mapper.CompanyMapper;
import com.ducthong.TopCV.domain.mapper.ImageMapper;
import com.ducthong.TopCV.domain.mapper.JobMapper;
import com.ducthong.TopCV.utility.TimeUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JobMapperImpl implements JobMapper {
    private final CompanyMapper companyMapper;
    private final ImageMapper imageMapper;

    @Override
    public JobResponseDTO toJobResponseDto(Job entity) {
        Company company = entity.getCompany();

        StringBuilder address = new StringBuilder();
        entity.getAddresses()
                .forEach(item -> address.append(item.getProvinceName()).append(", "));
        if (!entity.getAddresses().isEmpty()) address.setLength(address.length() - 2);

        return JobResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cities(address.toString())
                .company(Map.of(
                        "id", company.getId().toString(),
                        "name", company.getName(),
                        "urlCom", company.getUrlCom(),
                        "logo", company.getLogo().getImageUrl()))
                .salary(entity.getSalary())
                .build();
    }

    @Override
    public RelatedJobResponseDTO toRelatedJobResponseDto(Job entity, Boolean isApply) {
        Company company = entity.getCompany();

        // Address Job
        List<String> jobAddress =
                entity.getAddresses().stream().map(JobAddress::toString).toList();
        List<String> provinceList =
                entity.getAddresses().stream().map(Address::getProvinceName).toList();
        return RelatedJobResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .provinces(provinceList)
                .company(companyMapper.toCompanyResponseDto(company))
                .salary(entity.getSalary())
                .applyTime(TimeUtil.getHoursDifferenceNow(entity.getApplicationDueTime()))
                .lastUpdated(TimeUtil.getHoursDifferenceUpdate(entity.getLastUpdated()))
                .isApply(isApply)
                .build();
    }

    @Override
    public EmployerJobResponseDTO toEmployerJobResponseDto(Job entity) {
        return EmployerJobResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .postingTime(TimeUtil.toStringDateTime(entity.getPostingTime()))
                .numberOfView(entity.getNumberOfView())
                .numberOfApplicated(entity.getNumberOfApplicated())
                // .applicationRate((float) (entity.getNumberOfApplicated()/ entity.getNumberOfView()))
                .build();
    }

    @Override
    public DetailJobResponseDTO toDetailJobResponseDto(Job entity, Boolean isApply) {
        List<String> jobAddress =
                entity.getAddresses().stream().map(JobAddress::toString).toList();
        List<ImageResponseDTO> imageList = entity.getImageList().stream()
                .map(imageMapper::toImageResponseDto)
                .toList();
        // Industry
        Integer mainIndustry = 1;
        List<Integer> subIndustries = new ArrayList<>();
        for (IndustryJob item : entity.getIndustries()) {
            if (item.getIsMain()) mainIndustry = item.getIndustry().getId();
            else subIndustries.add(item.getIndustry().getId());
        }
        // Company
        Company company = entity.getCompany();
        return DetailJobResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .company(companyMapper.toBriefCompanyResponseDto(company))
                .address(jobAddress)
                .jobPosition(entity.getJobPosition().name())
                .numberOfVacancy(entity.getNumberOfVacancy())
                .workMethod(entity.getWorkMethod().name())
                .sexRequired(entity.getSexRequired().name())
                .salary(entity.getSalary())
                .jobExp(entity.getJobExp())
                .postingTime(TimeUtil.toStringDateTime(entity.getPostingTime()))
                .applicationDueTime(TimeUtil.toStringDateTime(entity.getApplicationDueTime()))
                .numberOfApplicated(entity.getNumberOfApplicated())
                .isVerified(entity.getIsVerified())
                .jobBenefit(entity.getJobBenefit())
                .jobDescript(entity.getJobDescript())
                .jobRequirement(entity.getJobRequirement())
                .addApplicationInfor(entity.getAddApplicationInfor())
                .lastUpdated(TimeUtil.toStringDateTime(entity.getLastUpdated()))
                .numberOfLike(entity.getNumberOfLike())
                .numberOfView(entity.getNumberOfView())
                .receiverName(entity.getName())
                .receiverPhone(entity.getReceiverPhone())
                .receiverEmail(entity.getReceiverEmail())
                .isApply(isApply)
                .applicationMethod(entity.getApplicationMethod().name())
                .imageList(imageList)
                .mainIndustry(mainIndustry)
                .subIndustries(subIndustries)
                .build();
    }

    @Override
    public DetailJobPageResponseDTO toDetailJobPageResponseDto(Job entity, Boolean isApply) {
        // Address Job
        List<String> jobAddress =
                entity.getAddresses().stream().map(JobAddress::toString).toList();
        List<String> provinceList =
                entity.getAddresses().stream().map(Address::getProvinceName).toList();
        // Image
        List<String> imageList = new ArrayList<>();
        if (!entity.getImageList().isEmpty()) {
            imageList = entity.getImageList().stream().map(Image::getImageUrl).toList();
        }
        // Industry
        // MainIndstry
        Map<String, String> mainIndustry = new HashMap<>();
        Optional<IndustryJob> mainIndustryOptional =
                entity.getIndustries().stream().filter(IndustryJob::getIsMain).findFirst();
        if (!mainIndustryOptional.isEmpty())
            mainIndustry = Map.of(
                    "id", mainIndustryOptional.get().getIndustry().getId().toString(),
                    "name", mainIndustryOptional.get().getIndustry().getName());
        // SubIndustry
        List<Map<String, String>> subIndustry = new ArrayList<>();
        List<IndustryJob> subIndustryOptional = entity.getIndustries().stream()
                .filter(item -> !item.getIsMain())
                .toList();
        if (!subIndustryOptional.isEmpty())
            subIndustryOptional.forEach(item -> subIndustry.add(Map.of(
                    "id", item.getIndustry().getId().toString(),
                    "name", item.getIndustry().getName())));
        // Company
        Company company = entity.getCompany();

        return DetailJobPageResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .company(companyMapper.toBriefCompanyResponseDto(company))
                .provinceList(provinceList)
                .address(jobAddress)
                .jobPosition(entity.getJobPosition().name())
                .numberOfVacancy(entity.getNumberOfVacancy())
                .workMethod(entity.getWorkMethod().name())
                .sexRequired(entity.getSexRequired().name())
                .salary(entity.getSalary())
                .jobExp(entity.getJobExp())
                .postingTime(TimeUtil.toStringDateTime(entity.getPostingTime()))
                .applicationDueTime(TimeUtil.toStringDateTime(entity.getApplicationDueTime()))
                .numberOfApplicated(entity.getNumberOfApplicated())
                .isVerified(entity.getIsVerified())
                .jobBenefit(entity.getJobBenefit())
                .jobDescript(entity.getJobDescript())
                .jobRequirement(entity.getJobRequirement())
                .addApplicationInfor(entity.getAddApplicationInfor())
                .lastUpdated(TimeUtil.toStringDateTime(entity.getLastUpdated()))
                .numberOfLike(entity.getNumberOfLike())
                .numberOfView(entity.getNumberOfView())
                .isApply(isApply)
                .applicationMethod(entity.getApplicationMethod().name())
                .imageList(imageList)
                .mainIndustry(mainIndustry)
                .subIndustries(subIndustry)
                .build();
    }

    @Override
    public Job jobRequestDtoToJobEntity(JobRequestDTO requestDTO) {
        return Job.builder()
                .name(requestDTO.name())
                .jobPosition(JobPosition.valueOf(requestDTO.jobPosition()))
                .numberOfVacancy(requestDTO.numberOfVacancy())
                .workMethod(WorkMethod.valueOf(requestDTO.workMethod()))
                .sexRequired(Gender.valueOf(requestDTO.sexRequired()))
                .salary(requestDTO.salary())
                .jobExp(requestDTO.jobExp())
                .postingTime(TimeUtil.getDateTimeNow())
                .applicationDueTime(TimeUtil.convertToDateTime(requestDTO.applicationDueTime()))
                .numberOfApplicated(0)
                .isVerified(false)
                .jobBenefit(requestDTO.jobBenefit())
                .jobDescript(requestDTO.jobDescript())
                .jobRequirement(requestDTO.jobRequirement())
                .addApplicationInfor(requestDTO.addApplicationInfor())
                .lastUpdated(null)
                .receiverName(requestDTO.receiverName())
                .receiverPhone(requestDTO.receiverPhone())
                .receiverEmail(requestDTO.receiverEmail())
                .isActive(requestDTO.isActive())
                .isTempDeleted(false)
                .tempDeletedTime(null)
                .numberOfLike(0)
                .numberOfView(0)
                .applicationMethod(ApplicationMethod.valueOf(requestDTO.applicationMethod()))
                .build();
    }

    @Override
    public Job updJobRequestDtoToJobEntity(UpdJobRequestDTO requestDTO, Job entity) {
        entity.setName(requestDTO.name());
        entity.setJobPosition(JobPosition.valueOf(requestDTO.jobPosition()));
        entity.setNumberOfVacancy(entity.getNumberOfVacancy());
        entity.setWorkMethod(entity.getWorkMethod());
        entity.setSexRequired(entity.getSexRequired());
        entity.setSalary(entity.getSalary());
        entity.setJobExp(entity.getJobExp());
        entity.setApplicationDueTime(entity.getApplicationDueTime());
        entity.setJobBenefit(entity.getJobBenefit());
        entity.setJobDescript(entity.getJobDescript());
        entity.setJobRequirement(entity.getJobRequirement());
        entity.setAddApplicationInfor(entity.getAddApplicationInfor());
        return entity;
    }

    @Override
    public JobAddressResponseDTO toJobAddressResponseDto(JobAddress entity) {
        return JobAddressResponseDTO.builder()
                .jobAddressId(entity.getId())
                .detail(entity.getDetail())
                .provinceCode(entity.getProvinceCode())
                .provinceName(entity.getProvinceName())
                .districtCode(entity.getDistrictCode())
                .districtName(entity.getDistrictName())
                .wardCode(entity.getWardCode())
                .wardName(entity.getWardName())
                .build();
    }
}
