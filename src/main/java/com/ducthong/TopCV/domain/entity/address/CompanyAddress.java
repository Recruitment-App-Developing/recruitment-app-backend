package com.ducthong.TopCV.domain.entity.address;

import com.ducthong.TopCV.domain.entity.Job;
import jakarta.persistence.*;

import com.ducthong.TopCV.domain.entity.Company;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import java.util.List;

@Entity(name = "CompanyAddress")
@Table(name = "company_addresses")
@Getter
@Setter
public class CompanyAddress extends Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private Boolean isVerified;
    private Boolean isMain;
    private Boolean isBranch;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;

    public CompanyAddress(String detail, String provinceName, String provinceCode, String districtName, String districtCode, String wardName, String wardCode) {
        super(detail, provinceName, provinceCode, districtName, districtCode, wardName, wardCode);
    }
}
