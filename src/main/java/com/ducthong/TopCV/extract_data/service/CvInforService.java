package com.ducthong.TopCV.extract_data.service;

import com.ducthong.TopCV.extract_data.dto.CvInforDTO;
import com.ducthong.TopCV.extract_data.dto.CvInforItem;
import com.ducthong.TopCV.extract_data.dto.DetailCvInforDTO;

import java.util.List;

public interface CvInforService {
    List<CvInforItem> getList();
    DetailCvInforDTO getDetail(String cvInforId);
}
