package com.ducthong.TopCV.extract_data.service;

import com.ducthong.TopCV.extract_data.dto.CvInforItem;
import com.ducthong.TopCV.extract_data.dto.DetailCvInforDTO;
import com.ducthong.TopCV.extract_data.dto.EmployerUploadCvRequest;
import com.ducthong.TopCV.responses.Response;

import java.util.List;

public interface CvInforService {
    List<CvInforItem> getList();
    DetailCvInforDTO getDetail(String cvInforId);
    Response uploadCvFiles(EmployerUploadCvRequest request);
    Response update(DetailCvInforDTO request);
    Response getCvInforAuditById(String id, String type);
    Response getCvInforAuditByCvInfoId(String cvInfoId, String type);
}
