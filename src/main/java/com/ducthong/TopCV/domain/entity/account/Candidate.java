package com.ducthong.TopCV.domain.entity.account;

import java.util.ArrayList;
import java.util.List;

import com.ducthong.TopCV.domain.entity.Application;
import com.ducthong.TopCV.domain.entity.CvProfile.CvProfile;
import com.ducthong.TopCV.domain.entity.CvProfile.Education;
import com.ducthong.TopCV.domain.entity.CvProfile.Experience;
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "cv_profile_id")
    private CvProfile cvProfile;
    // like Job List

    // role
}
