package com.ducthong.TopCV.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.ducthong.TopCV.domain.enums.ApplicationMethod;
import com.ducthong.TopCV.domain.enums.Gender;
import com.ducthong.TopCV.domain.enums.JobPosition;
import com.ducthong.TopCV.domain.enums.WorkMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Integer id;

    @NotBlank(message = "{job.valid.name}")
    private String name;

    @Valid
    @NotNull(message = "Address list cannot null")
    @Size(min = 1, max = 10, message = "Address list must contain at least one and at most 10 addresses")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "job_address",
            joinColumns = @JoinColumn(name = "job_id", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"job_id"}))
    List<@Valid @NotBlank(message = "Address cannot blank") String> address = new ArrayList<>();

    @Valid
    @NotNull(message = "Company cannot null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;

    @NotBlank(message = "The position of job cannot blank")
    @Enumerated(EnumType.STRING)
    private JobPosition jobPosition;

    @Size(min = 1, max = 100, message = "The number of vacancy must be greater than 0 and less than 100")
    @PositiveOrZero(message = "The number of vacancy must be a positive integer")
    private Integer numberOfVacancy;

    @NotBlank(message = "The work method cannot blank")
    @Enumerated(EnumType.STRING)
    private WorkMethod workMethod;

    @NotBlank(message = "The sex required of job cannot blank")
    @Enumerated(EnumType.STRING)
    private Gender sexRequired;

    @NotBlank(message = "The salary of job cannot blank")
    private String salary;

    @NotBlank(message = "The experience of cannot blank")
    private String jobExp;

    @NotBlank(message = "The posting time cannot blank")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date postingTime;

    @NotBlank(message = "The application due time cannot blank")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date applicationDueTime;

    @Size(min = 0, max = 100, message = "The number of applicated must be greater than or equal 0 and less than 100")
    @PositiveOrZero(message = "The number of applicated must be a positive integer")
    private Integer numberOfApplicated;

    private Boolean isVerified;

    private String jobBenefit;

    private String jobDescript;

    private String jobRequirement;

    private String addApplicationInfor;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date lastUpdated;

    private Boolean isActive;

    private Boolean isTempDeleted;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date tempDeletedTime;

    private Integer numberOfLike;

    private Integer numberOfView;

    @Enumerated(EnumType.STRING)
    private ApplicationMethod applicationMethod;

    @OneToMany(targetEntity = Image.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "job_id")
    private List<Image> imageList = new ArrayList<>();

    @ManyToOne(targetEntity = Industry.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "industry_id", referencedColumnName = "industry_id")
    private Industry industry;
}
