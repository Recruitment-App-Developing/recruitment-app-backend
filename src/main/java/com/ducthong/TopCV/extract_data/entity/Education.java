//package com.ducthong.TopCV.extract_data.entity;
//
//import com.ducthong.TopCV.utility.StringUtils;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "educations")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Education {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "education_id")
//    private String id;
//    private String school;
//    private String time;
//    private String industry;
//    private String description;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "cv_infor_id")
//    private CvInfor cvInfor;
//
//    public static boolean checkAllNull(Education education) {
//        return StringUtils.isNullOrEmpty(education.getSchool()) && StringUtils.isNullOrEmpty(education.getTime()) && StringUtils.isNullOrEmpty(education.getDescription());
//    }
//}
