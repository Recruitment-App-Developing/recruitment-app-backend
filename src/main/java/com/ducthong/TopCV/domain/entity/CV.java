package com.ducthong.TopCV.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.ducthong.TopCV.constant.TimeFormatConstant;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.enums.CvType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "CV")
@Table(name = "cvs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CV {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cv_id")
    private String id;

    private String name;

    private String cvLink;

    private String cvPublicId;

    @Enumerated(EnumType.STRING)
    private CvType cvType;

    @DateTimeFormat(pattern = TimeFormatConstant.DATETIME_FORMAT)
    private LocalDateTime whenCreated;

    @DateTimeFormat(pattern = TimeFormatConstant.DATETIME_FORMAT)
    private LocalDateTime lastUpdated;

    private Boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}
