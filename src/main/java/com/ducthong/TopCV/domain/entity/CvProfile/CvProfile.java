package com.ducthong.TopCV.domain.entity.CvProfile;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.ducthong.TopCV.domain.entity.account.Candidate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "CvProfile")
@Table(name = "cv_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CvProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cv_profile_id")
    private String id;

    @OneToOne(mappedBy = "cvProfile", fetch = FetchType.EAGER)
    private Candidate candidate;

    @OneToMany(mappedBy = "cvProfile", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations = new ArrayList<>();

    @OneToMany(mappedBy = "cvProfile", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences = new ArrayList<>();
}
