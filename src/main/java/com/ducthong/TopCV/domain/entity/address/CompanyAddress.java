package com.ducthong.TopCV.domain.entity.address;

import jakarta.persistence.*;

import com.ducthong.TopCV.domain.entity.Company;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity(name = "CompanyAddress")
@Table(name = "company_addresses")
@Getter
@Setter
@NoArgsConstructor
public class CompanyAddress extends Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private Boolean isVerified;
    private Boolean isMain;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;

    public CompanyAddress(
            String detail,
            String provinceName,
            String provinceCode,
            String districtName,
            String districtCode,
            String wardName,
            String wardCode) {
        super(detail, provinceName, provinceCode, districtName, districtCode, wardName, wardCode);
    }
    public CompanyAddress(String detail, Ward ward){
        super(detail,
                ward.getDistrict().getProvince().getName(),
                ward.getDistrict().getProvince().getCode(),
                ward.getDistrict().getName(),
                ward.getDistrict().getCode(),
                ward.getName(),
                ward.getCode());
        this.isVerified = false;
        this.isMain = false;
    }

    @Override
    public String toString() {
        return super.getDetail()+", "+super.getWardName()+", "
                +super.getDistrictName()+", "+super.getProvinceName()+", "+super.getProvinceName();
    }
}
