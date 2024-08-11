package com.ducthong.TopCV.domain.entity.address;

import jakarta.persistence.*;

import com.ducthong.TopCV.domain.entity.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "PersonAddress")
@Table(name = "person_addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonAddress extends Address {
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "address")
    private Account account;
}
