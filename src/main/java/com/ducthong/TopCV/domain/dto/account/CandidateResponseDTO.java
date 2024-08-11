package com.ducthong.TopCV.domain.dto.account;

import java.util.Date;

import com.ducthong.TopCV.domain.entity.Image;
import com.ducthong.TopCV.domain.entity.address.Address;
import com.ducthong.TopCV.domain.enums.Gender;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CandidateResponseDTO {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date dateOfBirth;
    private String email;
    private Image avatar;
    private String phoneNumber;
    private Address address;
    private Date lastUpdated;
    private Date lastLogIn;
    private Date whenCreated;
    private Date whenDeleted;
    private Boolean isFindJob;
}
