package com.ducthong.TopCV.extract_data.repository;

import com.ducthong.TopCV.extract_data.dto.AwardAuditDTO;

import java.util.List;

public interface CvInforAuditRepository {
    List<AwardAuditDTO> getListAwardAuditById(String id);

    List<AwardAuditDTO> getListAwardAuditByCvInfoId(String cvInfoId);
}
