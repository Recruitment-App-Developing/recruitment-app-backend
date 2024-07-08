package com.ducthong.TopCV.domain.entity.location;

import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "administrative_regions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministrativeRegion {
    @Id
    private Integer id;

    private String name;
    private String nameEn;
    private String codeName;
    private String codeNameEn;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "administrativeRegion")
    private List<Province> provinces;
}
