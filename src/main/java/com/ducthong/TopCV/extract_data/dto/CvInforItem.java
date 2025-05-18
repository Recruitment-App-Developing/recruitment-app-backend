package com.ducthong.TopCV.extract_data.dto;

public class CvInforItem {
    private String cvInforId;
    private String fullName;
    private String cvName;
    private String applicationPosition;
    private String address;
    private Integer awardQuantity;
    private Integer educationQuantity;
    private Integer experienceQuantity;

    public CvInforItem(String cvInforId, String fullName, String cvName, String applicationPosition, String address, Integer awardQuantity, Integer educationQuantity, Integer experienceQuantity) {
        this.cvInforId = cvInforId;
        this.fullName = fullName;
        this.cvName = cvName;
        this.applicationPosition = applicationPosition;
        this.address = address;
        this.awardQuantity = awardQuantity;
        this.educationQuantity = educationQuantity;
        this.experienceQuantity = experienceQuantity;
    }

    public String getCvName() {
        return cvName;
    }

    public void setCvName(String cvName) {
        this.cvName = cvName;
    }

    public String getCvInforId() {
        return cvInforId;
    }

    public void setCvInforId(String cvInforId) {
        this.cvInforId = cvInforId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getApplicationPosition() {
        return applicationPosition;
    }

    public void setApplicationPosition(String applicationPosition) {
        this.applicationPosition = applicationPosition;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAwardQuantity() {
        return awardQuantity;
    }

    public void setAwardQuantity(Integer awardQuantity) {
        this.awardQuantity = awardQuantity;
    }

    public Integer getEducationQuantity() {
        return educationQuantity;
    }

    public void setEducationQuantity(Integer educationQuantity) {
        this.educationQuantity = educationQuantity;
    }

    public Integer getExperienceQuantity() {
        return experienceQuantity;
    }

    public void setExperienceQuantity(Integer experienceQuantity) {
        this.experienceQuantity = experienceQuantity;
    }
}
