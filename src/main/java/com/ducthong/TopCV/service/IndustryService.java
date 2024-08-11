package com.ducthong.TopCV.service;

import java.util.List;

import com.ducthong.TopCV.domain.dto.industry.IndustryRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.domain.entity.Industry;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.responses.Response;

public interface IndustryService {
    MetaResponse<MetaResponseDTO, List<Industry>> getAllActiveIndustry(MetaRequestDTO requestDTO);

    MetaResponse<MetaResponseDTO, List<Industry>> getAllIndustry(MetaRequestDTO requestDTO);

    Response<Industry> getDetailActiveIndustry(Integer id);

    Response<Industry> getDetailAllIndustry(Integer id);

    Response<Industry> addIndustry(IndustryRequestDTO requestDTO);

    Response<Industry> updIndustry(Integer id, IndustryRequestDTO requestDTO);

    Response<?> deleteTempIndustry(Integer id);

    Response<?> deletePermIndustry(Integer id);
}
