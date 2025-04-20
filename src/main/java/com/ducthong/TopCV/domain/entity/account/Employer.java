package com.ducthong.TopCV.domain.entity.account;

import com.ducthong.TopCV.domain.entity.CV;
import jakarta.persistence.*;

import com.ducthong.TopCV.domain.entity.Company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Employer")
@Table(name = "employers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Employer extends Account {
    private Integer verifiedLevel;

    @ManyToOne(targetEntity = Company.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

//    @OneToMany(mappedBy = "employer", fetch = FetchType.LAZY)
//    private List<CV> cvs = new ArrayList<>();

    // Company id

    // Role
}
