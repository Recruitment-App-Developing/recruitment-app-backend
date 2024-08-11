package com.ducthong.TopCV.domain.entity.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Candidate")
@Table(name = "candidates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate extends Account {
    private Boolean isFindJob;

    // apply Job List

    // like Job List

    // role
}
