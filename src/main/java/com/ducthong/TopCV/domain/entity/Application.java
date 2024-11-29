package com.ducthong.TopCV.domain.entity;

import com.ducthong.TopCV.constant.TimeFormatConstant;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity(name = "Application")
@Table(name = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "application_id")
    private String id;

    private String cvLink;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @DateTimeFormat(pattern = TimeFormatConstant.DATETIME_FORMAT)
    private LocalDateTime applicationTime;

    @DateTimeFormat(pattern = TimeFormatConstant.DATETIME_FORMAT)
    private LocalDateTime lastUpdated;

    @DateTimeFormat(pattern = TimeFormatConstant.DATETIME_FORMAT)
    private LocalDateTime statusChangeTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    private Job job;
}
