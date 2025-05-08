package com.ducthong.TopCV.service;

import java.io.IOException;
import java.util.List;

import com.ducthong.TopCV.domain.dto.cv.CvRequestDTO;
import com.ducthong.TopCV.domain.dto.cv.CvResponseDTO;
import com.ducthong.TopCV.domain.dto.cv.UpdCvRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.domain.entity.CV;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.responses.Response;
import org.springframework.core.io.InputStreamResource;

public interface CvService {
    CV isCvAccess(String cvId, Integer accoutId);

    InputStreamResource getCvById(String cvId);

    MetaResponse<MetaResponseDTO, List<CvResponseDTO>> getListCvByAccountId(MetaRequestDTO metaRequestDTO);

    CvResponseDTO addCv(Integer candidateId, CvRequestDTO requestDTO) throws IOException;

    CvResponseDTO updateCv(Integer accountId, UpdCvRequestDTO requestDTO) throws IOException;

    Response<String> deleteCv(Integer accountId, String cvId);
}
