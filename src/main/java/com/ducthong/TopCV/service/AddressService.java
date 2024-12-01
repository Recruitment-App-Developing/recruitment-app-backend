package com.ducthong.TopCV.service;

import java.util.List;
import java.util.Map;

public interface AddressService {
    List<Map<String, String>> getListProvince();

    List<Map<String, String>> getListDistrictByProvince(String provinceId);

    List<Map<String, String>> getListWardByDistrict(String districtId);
}
