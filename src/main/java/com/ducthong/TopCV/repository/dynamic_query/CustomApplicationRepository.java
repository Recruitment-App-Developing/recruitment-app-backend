package com.ducthong.TopCV.repository.dynamic_query;

import com.ducthong.TopCV.domain.entity.Application;
import com.ducthong.TopCV.domain.enums.ApplicationStatus;


public interface CustomApplicationRepository {
    PagedResponse<Application> getListApplicationByAccountId(Integer accountId, ApplicationStatus applicationStatus, Integer pageSize, Integer pageNumber);
}
