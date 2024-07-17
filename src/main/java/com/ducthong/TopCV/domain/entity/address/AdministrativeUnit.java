package com.ducthong.TopCV.domain.entity.address;

import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "administrative_units")
@NoArgsConstructor
@AllArgsConstructor
public class AdministrativeUnit {
    @Id
    private Integer id;

    private String fullName;
    private String fullNameEn;
    private String shortName;
    private String shortNameEn;
    private String codeName;
    private String codeNameEn;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "administrativeUnit")
    private List<Province> provinces;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "administrativeUnit")
    private List<District> districts;
}
