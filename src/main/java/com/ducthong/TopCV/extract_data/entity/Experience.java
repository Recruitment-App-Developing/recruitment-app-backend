//package com.ducthong.TopCV.extract_data.entity;
//
//import com.ducthong.TopCV.utility.StringUtils;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "experiences")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Experience {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "experience_id")
//    private String id;
//    private String company;
//    private String time;
//    private String position;
//    private String description;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "cv_infor_id")
//    private CvInfor cvInfor;
//
//    public static boolean checkAllNull(Experience exp) {
//        return StringUtils.isNullOrEmpty(exp.getPosition()) && StringUtils.isNullOrEmpty(exp.getCompany())
//                && StringUtils.isNullOrEmpty(exp.getTime()) && StringUtils.isNullOrEmpty(exp.getDescription());
//    }
//}
