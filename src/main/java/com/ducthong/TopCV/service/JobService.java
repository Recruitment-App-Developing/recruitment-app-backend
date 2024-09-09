package com.ducthong.TopCV.service;

import com.ducthong.TopCV.domain.dto.job.DetailJobResponseDTO;
import com.ducthong.TopCV.domain.dto.job.JobRequestDTO;

import java.io.IOException;

public interface JobService {
DetailJobResponseDTO addJob(JobRequestDTO requestDTO, Integer userId) throws IOException;
}
