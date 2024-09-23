package com.ducthong.TopCV.domain.entity.account;

import java.util.ArrayList;
import java.util.List;

import com.ducthong.TopCV.domain.entity.Application;
import jakarta.persistence.*;

import com.ducthong.TopCV.domain.entity.CV;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Candidate")
@Table(name = "candidates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate extends Account {
    private Boolean isFindJob;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CV> cvs = new ArrayList<>();

    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = false)
    private List<Application> applications = new ArrayList<>();

    // like Job List

    // role
}
