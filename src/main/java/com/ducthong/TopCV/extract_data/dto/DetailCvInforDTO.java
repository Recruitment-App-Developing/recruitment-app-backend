package com.ducthong.TopCV.extract_data.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class DetailCvInforDTO {
    private String cvId;
    private String cvInforId;
    private String cvName;
    private String fullName;
    private String phone;
    private String email;
    private String dob;
    private String address;
    private String applicationPosition;
    private List<String> softSkills;
    private List<String> techSkills;
    private List<Award> awards;
    private List<Education> educations;
    private List<Experience> experiences;

    @Getter
    @Setter
    public static class Award {
        private String id;
        private String name;
        private String timeStr;
    }
    @Getter
    @Setter
    public static class Education {
        private String id;
        private String schoolName;
        private String industry;
        private String timeStr;
        private String detail;
    }
    @Getter
    @Setter
    public static class Experience {
        private String id;
        private String companyName;
        private String position;
        private String timeStr;
        private String detail;
    }
}