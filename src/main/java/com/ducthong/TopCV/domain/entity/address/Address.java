package com.ducthong.TopCV.domain.entity.address;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@AllArgsConstructor
@Getter
@Setter
public abstract class Address {
    private String detail;
    private String provinceName;
    private String provinceCode;
    private String districtName;
    private String districtCode;
    private String wardName;
    private String wardCode;

//    @Temporal(TemporalType.TIMESTAMP)
//    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
//    private Date whenCreated;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
//    private Date lastUpdated;
}
