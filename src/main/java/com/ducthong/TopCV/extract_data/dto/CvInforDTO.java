package com.ducthong.TopCV.extract_data.dto;

import com.ducthong.TopCV.utility.StringUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CvInforDTO {
    public String fullName;
    public String email;
    public String dateOfBirth;
    public String phone;
    public String address;
    public String positionApply;
    public List<Award> award;
    public List<Education> education;
    public List<Experience> experience;
    public List<String> technicalSkills;
    public List<String> softSkills;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Award {
        public String time;
        public String name;

        public static boolean checkAllNull(Award award) {
            return StringUtils.isNullOrEmpty(award.getName()) && StringUtils.isNullOrEmpty(award.getTime());
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Education {
        public String school;
        public String time;
        public String industry;
        public String description;

        public static boolean checkAllNull(Education education) {
            return StringUtils.isNullOrEmpty(education.getSchool()) && StringUtils.isNullOrEmpty(education.getTime())
                   && StringUtils.isNullOrEmpty(education.getIndustry()) && StringUtils.isNullOrEmpty(education.getDescription());
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Experience {
        public String company;
        public String time;
        public String position;
        public String description;

        public static boolean checkAllNull(Experience exp) {
            return StringUtils.isNullOrEmpty(exp.getPosition()) && StringUtils.isNullOrEmpty(exp.getCompany())
                    && StringUtils.isNullOrEmpty(exp.getTime()) && StringUtils.isNullOrEmpty(exp.getDescription());
        }
    }
}
