package com.ducthong.TopCV.service;

import java.io.IOException;
import java.util.List;

import com.ducthong.TopCV.domain.dto.job.*;
import com.ducthong.TopCV.domain.dto.job.job_address.JobAddressRequestDTO;
import com.ducthong.TopCV.domain.dto.job.job_address.JobAddressResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.domain.entity.Job;
import com.ducthong.TopCV.repository.dynamic_query.PagedResponse;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.responses.Response;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface JobService {
    Job isVerifiedJob(Integer jobId, Integer accountId);

    Boolean isApply(Integer jobId, Integer accountId);

    DetailJobResponseDTO getDetailJob(Integer jobId);

    DetailJobPageResponseDTO getDetailJobPage(Integer jobId);

    PagedResponse searchJob(SearchJobRequestDTO requestDTO, MetaRequestDTO metaRequestDTO);

    MetaResponse<MetaResponseDTO, List<EmployerJobResponseDTO>> getListJobByCompany(
            SearchJobByCompanyRequestDTO requestDTO, Integer accountId, MetaRequestDTO metaRequestDTO);

    MetaResponse<MetaResponseDTO, List<RelatedJobResponseDTO>> findListJobByCompany(
            MetaRequestDTO metaRequestDTO, Integer companyId, String name, String address);

    MetaResponse<MetaResponseDTO, List<JobResponseDTO>> getListJobSpecification(MetaRequestDTO metaRequestDTO);

    MetaResponse<MetaResponseDTO, List<JobResponseDTO>> getListJob(MetaRequestDTO metaRequestDTO) throws JsonProcessingException;

    DetailJobResponseDTO addJob(JobRequestDTO requestDTO, Integer userId) throws IOException;

    DetailJobResponseDTO updateJob(UpdJobRequestDTO requestDTO, Integer userId, Integer jobId);

    Response hiddenJob(List<Integer> jobIds);

    List<JobAddressResponseDTO> updateJobAddress(JobAddressRequestDTO requestDTO, Integer accountId, Integer jobId);

    List<JobAddressResponseDTO> getListJobAddressByJob(Integer accountId, Integer jobId);

    List<JobAddressResponseDTO> deleteJobAddressId(Integer accountId, Integer jobId, Integer jobAddressId);
}
