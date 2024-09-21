package com.ducthong.TopCV.service;

import java.io.IOException;
import java.util.List;

import com.ducthong.TopCV.domain.dto.cv.CvRequestDTO;
import com.ducthong.TopCV.domain.dto.cv.CvResponseDTO;
import com.ducthong.TopCV.domain.dto.cv.UpdCvRequestDTO;
import com.ducthong.TopCV.responses.Response;

public interface CvService {
    List<CvResponseDTO> getListCvByAccountId(Integer candidateId);

    CvResponseDTO addCv(Integer candidateId, CvRequestDTO requestDTO) throws IOException;

    CvResponseDTO updateCv(Integer accountId, UpdCvRequestDTO requestDTO) throws IOException;

    Response<String> deleteCv(Integer accountId, String cvId);
}
