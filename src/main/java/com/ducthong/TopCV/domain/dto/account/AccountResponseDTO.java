package com.ducthong.TopCV.domain.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponseDTO {
    private Integer id;
    private String username;
    private String fullName;
    private String avatar;
    private String phoneNumber;
    private String email;
}
