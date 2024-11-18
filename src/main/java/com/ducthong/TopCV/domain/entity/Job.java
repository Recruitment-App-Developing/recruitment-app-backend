package com.ducthong.TopCV.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.ducthong.TopCV.constant.TimeFormatConstant;
import com.ducthong.TopCV.domain.entity.address.JobAddress;
import com.ducthong.TopCV.domain.enums.ApplicationMethod;
import com.ducthong.TopCV.domain.enums.Gender;
import com.ducthong.TopCV.domain.enums.JobPosition;
import com.ducthong.TopCV.domain.enums.WorkMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Integer id;

    @NotBlank(message = "{job.valid.name}")
    private String name;

    @OneToMany(mappedBy = "job", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobAddress> addresses = new ArrayList<>();

    @Valid
    @NotNull(message = "Company cannot null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;

    @Enumerated(EnumType.STRING)
    private JobPosition jobPosition;

    @Min(value = 1, message = "The number of vacancy must be greater than 0")
    @Max(value = 100, message = "The number of vacancy must less than 100")
    @PositiveOrZero(message = "The number of vacancy must be a positive integer")
    private Integer numberOfVacancy;

    @Enumerated(EnumType.STRING)
    private WorkMethod workMethod;

    @Enumerated(EnumType.STRING)
    private Gender sexRequired;

    @NotBlank(message = "The salary of job cannot blank")
    private String salary;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    @NotBlank(message = "The experience of cannot blank")
    private String jobExp;

    @DateTimeFormat(pattern = TimeFormatConstant.DATETIME_FORMAT)
    private LocalDateTime postingTime;

    @DateTimeFormat(pattern = TimeFormatConstant.DATETIME_FORMAT)
    private LocalDateTime applicationDueTime;

    @Min(value = 0, message = "The number of applicated must be at least 0")
    @PositiveOrZero(message = "The number of applicated must be a positive integer")
    private Integer numberOfApplicated;

    private Boolean isVerified;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String jobBenefit;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String jobDescript;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String jobRequirement;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String addApplicationInfor;

    @DateTimeFormat(pattern = TimeFormatConstant.DATETIME_FORMAT)
    private LocalDateTime lastUpdated;

    private Boolean isActive;

    private Boolean isTempDeleted;

    @DateTimeFormat(pattern = TimeFormatConstant.DATETIME_FORMAT)
    private LocalDateTime tempDeletedTime;

    private Integer numberOfLike;

    private Integer numberOfView;

    @Enumerated(EnumType.STRING)
    private ApplicationMethod applicationMethod;

    private String receiverName;

    private String receiverPhone;

    private String receiverEmail;

    @OneToMany(targetEntity = Image.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "job_id")
    private List<Image> imageList = new ArrayList<>();

    @OneToMany(mappedBy = "job", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IndustryJob> industries = new ArrayList<>();

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications = new ArrayList<>();
}
