package com.ducthong.TopCV.domain.entity.location;

import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "provinces")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Province {
    @Id
    private String code;

    private String name;
    private String nameEn;
    private String fullName;
    private String fullNameEn;
    private String codeName;

    @ManyToOne
    @JoinColumn(name = "administrative_unit_id")
    @JsonIgnore
    private AdministrativeUnit administrativeUnit;

    @ManyToOne
    @JoinColumn(name = "administrative_region_id")
    @JsonIgnore
    private AdministrativeRegion administrativeRegion;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "province")
    @JsonIgnore
    private List<District> districts;
}
