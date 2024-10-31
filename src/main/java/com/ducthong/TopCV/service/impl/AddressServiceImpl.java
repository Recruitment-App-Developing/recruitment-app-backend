package com.ducthong.TopCV.service.impl;

import com.ducthong.TopCV.domain.entity.address.District;
import com.ducthong.TopCV.domain.entity.address.Province;
import com.ducthong.TopCV.repository.address.DistrictRepository;
import com.ducthong.TopCV.repository.address.ProvinceRepository;
import com.ducthong.TopCV.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final ProvinceRepository provinceRepo;
    private final DistrictRepository districtRepo;
    @Override
    public List<Map<String, String>> getListProvince() {
        List<Province> provinceList = provinceRepo.findAll();
        List<Map<String, String>> res = provinceList.stream().
                map(item -> Map.of(
                        "id", item.getCode(),
                        "name", item.getName()
                )).toList();

        return res;
    }

    @Override
    public List<Map<String, String>> getListDistrictByProvince(String provinceId) {
        Optional<Province> provinceOptional = provinceRepo.findById(provinceId);
        List<Map<String, String>> res = provinceOptional.get().getDistricts().stream().
                map(item -> Map.of(
                        "id", item.getCode(),
                        "name", item.getName()
                )).toList();

        return res;
    }

    @Override
    public List<Map<String, String>> getListWardByDistrict(String districtId) {
        Optional<District> districtOptional = districtRepo.findById(districtId);
        List<Map<String, String>> res = districtOptional.get().getWards().stream().
                map(item -> Map.of(
                        "id", item.getCode(),
                        "name", item.getName()
                )).toList();

        return res;
    }
}
