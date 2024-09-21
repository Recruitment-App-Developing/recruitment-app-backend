package com.ducthong.TopCV.utility;

import java.util.List;

import com.ducthong.TopCV.domain.entity.address.Address;

public class AddressUtil {
    public static String toCities(List<String> addresses) {
        //        StringBuilder cities = new StringBuilder();
        //        addresses.forEach(
        //                item -> {
        //                    cities.append(item.getProvinceName()).append(", ");
        //                }
        //        );
        //        if (cities.length() > 0) cities.setLength(cities.length() - 2);
        //
        //        return cities.toString();
        // TODO
        return "";
    }

    public static String toString(Address address) {
        //        StringBuilder res = new StringBuilder();
        //        res.append(address.getDetail()).append(", ")
        //                .append(address.getWardName()).append(", ")
        //                .append(address.getDistrictName()).append(", ")
        //                .append(address.getProvinceName());
        //        return res.toString();
        return "";
    }

    public static String toStringJobAddress(Address address) {
        //        String res = address.getProvinceName()+": "+address.getDetail()+", "+address.getWardName()+",
        // "+address.getDistrictName()+", "+address.getProvinceName();
        //        return res;
        return "";
    }
}
