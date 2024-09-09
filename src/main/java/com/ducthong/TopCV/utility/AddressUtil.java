package com.ducthong.TopCV.utility;

import com.ducthong.TopCV.domain.entity.address.Address;

public class AddressUtil {
    public static String toStringJobAddress(Address address){
        String res = address.getProvinceName()+": "+address.getDetail()+", "+address.getWardName()+", "+address.getDistrictName()+", "+address.getProvinceName();
        return res;
    }
}
