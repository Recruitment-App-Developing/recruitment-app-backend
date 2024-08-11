package com.ducthong.TopCV.domain.entity.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "Employer")
@Table(name = "employers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Employer extends Account {
    private Integer verifiedLevel;

    // Company id

    // Role
}
