package com.ducthong.TopCV.domain.entity.address;

import jakarta.persistence.*;

import com.ducthong.TopCV.domain.entity.Company;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "CompanyAddress")
@Table(name = "company_addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAddress extends Address {
    private Boolean isVerified;
    private Boolean isMain;
    private Boolean isBranch;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;
}
