package com.ducthong.TopCV.domain.dto.account;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountResponseDTO {
    private Integer id;
    private String username;
    private String fullName;
    private String avatar;
    private String phoneNumber;
    private String email;
    private String cvProfileId;
}
