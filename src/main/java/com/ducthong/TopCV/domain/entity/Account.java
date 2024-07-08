package com.ducthong.TopCV.domain.entity;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.ducthong.TopCV.domain.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Boolean deleted;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date dateOfBirth;

    @Email(message = "Invalid email format")
    private String email;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id", referencedColumnName = "image_id")
    private Image avatar;

    @Pattern(regexp = "^(0[3|5|7|8|9])+([0-9]{8})$", message = "Invalid phone format")
    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date lastUpdated;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date lastLogIn;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date whenCreated;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date whenDeleted;
}
