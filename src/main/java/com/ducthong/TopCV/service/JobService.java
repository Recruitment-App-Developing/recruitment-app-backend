package com.ducthong.TopCV.service;

import java.io.IOException;
import java.util.List;

import com.ducthong.TopCV.domain.dto.job.*;
import com.ducthong.TopCV.domain.dto.job.job_address.JobAddressRequestDTO;
import com.ducthong.TopCV.domain.dto.job.job_address.JobAddressResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.domain.entity.Job;
import com.ducthong.TopCV.responses.MetaResponse;

public interface JobService {
    Job isVerifiedJob(Integer jobId, Integer accountId);

    Boolean isApply(Integer jobId, Integer accountId);

    DetailJobResponseDTO getDetailJob(Integer jobId);

    DetailJobPageResponseDTO getDetailJobPage(Integer jobId);

    List<RelatedJobResponseDTO> searchJob(SearchJobRequestDTO requestDTO);

    MetaResponse<MetaResponseDTO, List<EmployerJobResponseDTO>> getListJobByCompany(
            MetaRequestDTO metaRequestDTO, Integer accountId);

    MetaResponse<MetaResponseDTO, List<RelatedJobResponseDTO>> findListJobByCompany(
            MetaRequestDTO metaRequestDTO, Integer companyId, String name, String address);

    MetaResponse<MetaResponseDTO, List<JobResponseDTO>> getListJobSpecification(MetaRequestDTO metaRequestDTO);

    MetaResponse<MetaResponseDTO, List<JobResponseDTO>> getListJob(MetaRequestDTO metaRequestDTO);

    DetailJobResponseDTO addJob(JobRequestDTO requestDTO, Integer userId) throws IOException;

    DetailJobResponseDTO updateJob(UpdJobRequestDTO requestDTO, Integer userId, Integer jobId);

    List<JobAddressResponseDTO> updateJobAddress(JobAddressRequestDTO requestDTO, Integer accountId, Integer jobId);

    List<JobAddressResponseDTO> getListJobAddressByJob(Integer accountId, Integer jobId);

    List<JobAddressResponseDTO> deleteJobAddressId(Integer accountId, Integer jobId, Integer jobAddressId);
}
