package com.ducthong.TopCV.domain.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.ducthong.TopCV.domain.entity.account.Employer;
import com.ducthong.TopCV.domain.entity.address.CompanyAddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Integer id;

    private String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "logo_id", referencedColumnName = "image_id")
    private Image logo;

    private String urlCom;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyAddress> addressList = new ArrayList<>();

    private String employeeScale;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobList = new ArrayList<>();

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Employer> employerList = new ArrayList<>();

    private Integer numberOfFollow;

    private String taxCode;

    private String email;

    private String phoneNumber;

    private String activeFields;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String briefIntro;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String detailIntro;
}
