package com.ducthong.TopCV.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.redis_service.JobRedisService;
import com.ducthong.TopCV.utility.Common;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.ducthong.TopCV.constant.meta.MetaConstant;
import com.ducthong.TopCV.domain.dto.job.*;
import com.ducthong.TopCV.domain.dto.job.job_address.JobAddressRequestDTO;
import com.ducthong.TopCV.domain.dto.job.job_address.JobAddressResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.SortingDTO;
import com.ducthong.TopCV.domain.entity.*;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.entity.account.Employer;
import com.ducthong.TopCV.domain.entity.address.JobAddress;
import com.ducthong.TopCV.domain.mapper.AddressMapper;
import com.ducthong.TopCV.domain.mapper.JobMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.*;
import com.ducthong.TopCV.repository.address.JobAddressRepository;
import com.ducthong.TopCV.repository.dynamic_query.CustomJobRepository;
import com.ducthong.TopCV.repository.dynamic_query.PagedResponse;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.service.ImageService;
import com.ducthong.TopCV.service.JobService;
import com.ducthong.TopCV.utility.AuthUtil;
import com.ducthong.TopCV.utility.GetRoleUtil;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableTransactionManagement
public class JobServiceImpl implements JobService {
    // Repository
    private final IndustryRepository industryRepo;
    private final JobRepository jobRepo;
    private final ApplicationRepository applicationRepo;
    private final IndustryJobRepository industryJobRepo;
    private final JobAddressRepository jobAddressRepo;
    private final CompanyRepository companyRepo;
    private final CandidateRepository candidateRepo;
    private final CustomJobRepository customJobRepo;
    // Service
    private final ImageService imageService;
    private final JobRedisService jobRedisService;
    // Mapper
    private final JobMapper jobMapper;
    private final AddressMapper addressMapper;
    // Varient
    @Value("${cloudinary.folder.job}")
    private String JOB_FOLDER;

    @Override
    public Job isVerifiedJob(Integer jobId, Integer accountId) {
        Employer employer = GetRoleUtil.getEmployer(accountId);

        Optional<Job> findJob = jobRepo.findById(jobId);
        if (findJob.isEmpty()) throw new AppException("This job is not existed");
        Job job = findJob.get();

        if (job.getCompany().getId() != employer.getCompany().getId())
            throw new AppException("This account does not have permissions");
        return job;
    }

    @Override
    @Transactional
    public Boolean isApply(Integer jobId, Integer accountId) {
        if (accountId == null) return false;
        Optional<Candidate> candidate = candidateRepo.findById(accountId);
        if (candidate.isEmpty()) return false;
        for (Application item : candidate.get().getApplications())
            if (Objects.equals(item.getJob().getId(), jobId)) return true;

        return false;
    }

    @Override
    @Transactional
    public DetailJobResponseDTO getDetailJob(Integer jobId) {
        Optional<Job> findJob = jobRepo.findById(jobId);
        if (findJob.isEmpty()) throw new AppException("This job is not existed");

        Boolean isApply = false;
        if (AuthUtil.getRequestedUser() != null
                && applicationRepo.checkAccountAppliedJob(
                        jobId, AuthUtil.getRequestedUser().getId())) isApply = true;

        return jobMapper.toDetailJobResponseDto(findJob.get(), isApply);
    }

    @Override
    @Transactional
    public DetailJobPageResponseDTO getDetailJobPage(Integer jobId) {
        Optional<Job> findJob = jobRepo.findById(jobId);
        if (findJob.isEmpty()) throw new AppException("This job is not existed");

        Boolean isApply = false;
        if (AuthUtil.getRequestedUser() != null
                && applicationRepo.checkAccountAppliedJob(
                        jobId, AuthUtil.getRequestedUser().getId())) isApply = true;

        return jobMapper.toDetailJobPageResponseDto(findJob.get(), isApply);
    }

    @Override
    public PagedResponse searchJob(SearchJobRequestDTO requestDTO, MetaRequestDTO metaRequestDTO) {
        PagedResponse<Job> res = customJobRepo.searchJob(requestDTO, metaRequestDTO);
        PagedResponse<RelatedJobResponseDTO> response = new PagedResponse<>();
        BeanUtils.copyProperties(res, response);
        if (AuthUtil.getRequestedUser() != null) {
            response.setContent(res.getContent().stream()
                    .map(item -> jobMapper.toRelatedJobResponseDto(
                            item,
                            applicationRepo.checkAccountAppliedJob(
                                    item.getId(), AuthUtil.getRequestedUser().getId())))
                    .toList());
        } else {
            response.setContent(res.getContent().stream()
                    .map(item -> jobMapper.toRelatedJobResponseDto(item, false))
                    .toList());
        }
        return response;
    }

    @Override
    @Transactional
    public MetaResponse<MetaResponseDTO, List<EmployerJobResponseDTO>> getListJobByCompany(
            SearchJobByCompanyRequestDTO requestDTO, Integer accountId, MetaRequestDTO metaRequestDTO) {
        Employer employer = GetRoleUtil.getEmployer(accountId);
        if (employer.getCompany().getId() == null) throw new AppException("This account doesn't register company");

        //        Sort sort = metaRequestDTO.sortDir().equals(MetaConstant.Sorting.DEFAULT_DIRECTION)
        //                ? Sort.by(metaRequestDTO.sortField()).ascending()
        //                : Sort.by(metaRequestDTO.sortField()).descending();
        //        Pageable pageable = PageRequest.of(metaRequestDTO.currentPage(), metaRequestDTO.pageSize(), sort);
        //
        //        Page<Job> page = jobRepo.getListJobByCompany(pageable, employer.getCompany().getId());
        //
        //        if (page.getContent().isEmpty()) throw new AppException("List job is empty");
        PagedResponse<Job> page = customJobRepo.searchJobByCompany(
                requestDTO, employer.getCompany().getId(), metaRequestDTO);

        List<EmployerJobResponseDTO> li = page.getContent().stream()
                .map(jobMapper::toEmployerJobResponseDto)
                .toList();
        return MetaResponse.successfulResponse(
                "Get list job success",
                MetaResponseDTO.builder()
                        .totalItems((int) page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .currentPage(metaRequestDTO.currentPage())
                        .pageSize(metaRequestDTO.pageSize())
                        .sorting(null)
                        .build(),
                li);
    }

    @Override
    public MetaResponse<MetaResponseDTO, List<RelatedJobResponseDTO>> findListJobByCompany(
            MetaRequestDTO metaRequestDTO, Integer companyId, String name, String address) {
        Optional<Company> companyOp = companyRepo.findById(companyId);
        if (companyOp.isEmpty()) throw new AppException("Công ty này không tồn tại");

        Sort sort = metaRequestDTO.sortDir().equals(MetaConstant.Sorting.DEFAULT_DIRECTION)
                ? Sort.by(metaRequestDTO.sortField()).ascending()
                : Sort.by(metaRequestDTO.sortField()).descending();
        Pageable pageable = PageRequest.of(metaRequestDTO.currentPage(), metaRequestDTO.pageSize(), sort);

        Page<Job> page = jobRepo.findListJobByCompany(pageable, companyId, name, address);

        List<RelatedJobResponseDTO> li = new ArrayList<>();
        if (AuthUtil.getRequestedUser() != null) {
            li = page.getContent().stream()
                    .map(item -> jobMapper.toRelatedJobResponseDto(
                            item,
                            isApply(item.getId(), AuthUtil.getRequestedUser().getId())))
                    .toList();
        } else {
            li = page.getContent().stream()
                    .map(item -> jobMapper.toRelatedJobResponseDto(item, false))
                    .toList();
        }

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

        List<JobResponseDTO> res =
                jobList.stream().map(item -> jobMapper.toJobResponseDto(item)).toList();
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
    public MetaResponse<MetaResponseDTO, List<JobResponseDTO>> getListJob(MetaRequestDTO metaRequestDTO) throws JsonProcessingException {
        Sort sort = metaRequestDTO.sortDir().equals(MetaConstant.Sorting.DEFAULT_DIRECTION)
                ? Sort.by(metaRequestDTO.sortField()).ascending()
                : Sort.by(metaRequestDTO.sortField()).descending();
        Pageable pageable = PageRequest.of(metaRequestDTO.currentPage(), metaRequestDTO.pageSize(), sort);
        Page<Job> page = jobRepo.findAll(pageable);
        if (page.getContent().isEmpty()) throw new AppException("List car is empty");
        List<JobResponseDTO> li = page.getContent().stream()
                .map(temp -> jobMapper.toJobResponseDto(temp))
                .toList();
        MetaResponse<MetaResponseDTO, List<JobResponseDTO>> res = MetaResponse.successfulResponse(
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
        // Cache in Redis
        try {
            jobRedisService.saveListJob(metaRequestDTO, res);
        } catch (Exception e) {
            log.error("Error save redis");
        }

        return res;
    }

    @Override
    @Transactional
    public DetailJobResponseDTO addJob(JobRequestDTO requestDTO, Integer accountId) throws IOException {
        Employer employer = GetRoleUtil.getEmployer(accountId);
        if (employer.getCompany() == null) throw new AppException("Tài khoản này chưa đăng kí công ty");

        Job newJob = jobMapper.jobRequestDtoToJobEntity(requestDTO);
        // Industry
        List<IndustryJob> industryJobs = new ArrayList<>();

        Optional<Industry> mainIndustryFind = industryRepo.findById(requestDTO.mainIndustry());
        if (mainIndustryFind.isEmpty()) throw new AppException("Ngành nghề chính này không tồn tại");
        IndustryJob mainIndustryJob = IndustryJob.builder()
                .industry(mainIndustryFind.get())
                .job(newJob)
                .isMain(true)
                .build();
        industryJobs.add(mainIndustryJob);
        requestDTO.subIndustries().forEach(item -> {
            Optional<Industry> temp = industryRepo.findById(item);
            if (temp.isEmpty()) throw new AppException("Ngành nghề này không tồn tại");
            IndustryJob industryJob = IndustryJob.builder()
                    .industry(temp.get())
                    .job(newJob)
                    .isMain(false)
                    .build();
            industryJobs.add(industryJob);
        });
        industryJobs.add(mainIndustryJob);
        newJob.setIndustries(industryJobs);
        // Company
        newJob.setCompany(employer.getCompany());
        // Job Image
        List<Image> imageList = new ArrayList<>();
        if (!requestDTO.imageList().isEmpty()) {
            imageList = imageService.uploadListBase64Image(requestDTO.imageList(), JOB_FOLDER);
        }
        newJob.setImageList(imageList);
        // Address
        List<JobAddress> jobAddressList = requestDTO.addressList().stream()
                .map(item -> {
                    try {
                        String[] temp = item.split(";");
                        JobAddress jobAddress = addressMapper.toJobAddress(temp[0], temp[1]);
                        jobAddress.setJob(newJob);
                        return jobAddress;
                    } catch (Exception e) {
                        throw new AppException("Địa chỉ này không đúng");
                    }
                })
                .toList();
        newJob.setAddresses(jobAddressList);
        // Salary
        ProcessSalaryRequest salaryRequest =
                new ProcessSalaryRequest(requestDTO.salaryType(), requestDTO.salaryUnit(), requestDTO.salaryFrom(), requestDTO.salaryTo());
        newJob.setSalary(Common.convertToSalry(salaryRequest));
        try {
            Job saveJob = jobRepo.save(newJob);
            return jobMapper.toDetailJobResponseDto(saveJob, false);
        } catch (Exception e) {
            throw new AppException("Thêm mới tin tuyển dụng thất bại");
        }
    }

    @Override
    @Transactional
    public DetailJobResponseDTO updateJob(UpdJobRequestDTO requestDTO, Integer accountId, Integer jobId) {
        Job job = isVerifiedJob(jobId, accountId);

        Job updJob = jobMapper.updJobRequestDtoToJobEntity(requestDTO, job);
        // Industry
        List<Integer> updIndustryRequest = requestDTO.subIndustries();
        updIndustryRequest.add(requestDTO.mainIndustry());
        List<IndustryJob> oldIndustryJobs = job.getIndustries();
        // Sub Industry
        int oldIndustryJobs_Size = oldIndustryJobs.size();
        if (oldIndustryJobs_Size > updIndustryRequest.size())
            for (int i = updIndustryRequest.size(); i < oldIndustryJobs_Size; i++) {
                industryJobRepo.deleteById(oldIndustryJobs.getLast().getId());
                oldIndustryJobs.remove(oldIndustryJobs.getLast());
            }
        for (int i = 0; i < oldIndustryJobs.size(); i++) {
            if (oldIndustryJobs.get(i).getIndustry().getId() != updIndustryRequest.get(i)) {
                Industry industry =
                        industryRepo.findById(updIndustryRequest.get(i)).get();
                oldIndustryJobs.get(i).setIndustry(industry);
            }
            oldIndustryJobs.get(i).setIsMain(false);
        }
        if (oldIndustryJobs.size() < updIndustryRequest.size()) {
            for (int i = oldIndustryJobs.size(); i < updIndustryRequest.size(); i++) {
                Industry industry =
                        industryRepo.findById(updIndustryRequest.get(i)).get();
                oldIndustryJobs.add(new IndustryJob(industry, updJob, false));
            }
        }
        updJob.setIndustries(oldIndustryJobs);
        // Main Industry
        for (IndustryJob item : updJob.getIndustries())
            if (item.getIndustry().getId() == requestDTO.mainIndustry()) {
                item.setIsMain(true);
                break;
            }
        try {
            Job saveJob = jobRepo.save(updJob);
            return jobMapper.toDetailJobResponseDto(saveJob, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response hiddenJob(List<Integer> jobIds) {
        Employer employer = GetRoleUtil.getEmployer(AuthUtil.getRequestedUser().getId());
        Company company = employer.getCompany();
        List<Job> jobList = jobRepo.findJobByComIdAndListIds(company.getId(), jobIds);
        jobList.forEach(item -> item.setIsActive(false));
        jobRepo.saveAll(jobList);
        return Response.successfulResponse("Ẩn tin tuyển dụng thành công");
    }

    @Override
    public List<JobAddressResponseDTO> updateJobAddress(
            JobAddressRequestDTO requestDTO, Integer accountId, Integer jobId) {
        Job job = isVerifiedJob(jobId, accountId);
        JobAddress newJobAddress = addressMapper.toJobAddress(requestDTO.detail(), requestDTO.wardCode());
        if (requestDTO.jobAddressId() != null) {
            Optional<JobAddress> jobAddressFind = job.getAddresses().stream()
                    .filter(item -> item.getId() == requestDTO.jobAddressId())
                    .findFirst();
            if (jobAddressFind.isEmpty()) throw new AppException("This address does not found");
            newJobAddress.setId(jobAddressFind.get().getId());
        }
        newJobAddress.setJob(job);

        jobAddressRepo.save(newJobAddress);

        return jobRepo.findById(jobId).get().getAddresses().stream()
                .map(jobMapper::toJobAddressResponseDto)
                .toList();
    }

    @Override
    public List<JobAddressResponseDTO> getListJobAddressByJob(Integer accountId, Integer jobId) {
        Job job = isVerifiedJob(jobId, accountId);

        return job.getAddresses().stream()
                .map(jobMapper::toJobAddressResponseDto)
                .toList();
    }

    @Override
    public List<JobAddressResponseDTO> deleteJobAddressId(Integer accountId, Integer jobId, Integer jobAddressId) {
        Job job = isVerifiedJob(jobId, accountId);
        Optional<JobAddress> jobAddressFind = job.getAddresses().stream()
                .filter(item -> item.getId() == jobAddressId)
                .findFirst();
        if (jobAddressFind.isEmpty()) throw new AppException("This address does not found");
        jobAddressRepo.deleteById(jobAddressId);

        return jobRepo.findById(jobId).get().getAddresses().stream()
                .map(jobMapper::toJobAddressResponseDto)
                .toList();
    }
}
