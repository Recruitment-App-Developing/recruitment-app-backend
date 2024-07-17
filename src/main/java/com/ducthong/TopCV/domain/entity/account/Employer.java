package com.ducthong.TopCV.domain.entity.account;

import com.ducthong.TopCV.domain.entity.account.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Employer")
@Table(name = "employers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employer extends Account {
    private Integer verifiedLevel;

    // Company id

    // Role
}
