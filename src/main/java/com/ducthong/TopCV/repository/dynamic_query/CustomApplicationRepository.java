package com.ducthong.TopCV.repository.dynamic_query;

import com.ducthong.TopCV.domain.dto.candidate.SearchCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.entity.Application;
import com.ducthong.TopCV.domain.enums.ApplicationStatus;

public interface CustomApplicationRepository {
    PagedResponse<Application> searchCandidateByJob(
            SearchCandidateRequestDTO requestDTO, Integer jobId, MetaRequestDTO metaRequestDTO);

    PagedResponse<Application> getListApplicationByAccountId(
            Integer accountId, ApplicationStatus applicationStatus, Integer pageSize, Integer pageNumber);
}
