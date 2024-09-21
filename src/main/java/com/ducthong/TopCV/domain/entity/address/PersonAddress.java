package com.ducthong.TopCV.domain.entity.address;

import jakarta.persistence.*;

import com.ducthong.TopCV.domain.entity.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity(name = "PersonAddress")
@Table(name = "person_addresses")
@Getter
@Setter
public class PersonAddress extends Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "address")
    private Account account;

    public PersonAddress(
            String detail,
            String provinceName,
            String provinceCode,
            String districtName,
            String districtCode,
            String wardName,
            String wardCode) {
        super(detail, provinceName, provinceCode, districtName, districtCode, wardName, wardCode);
    }
}
