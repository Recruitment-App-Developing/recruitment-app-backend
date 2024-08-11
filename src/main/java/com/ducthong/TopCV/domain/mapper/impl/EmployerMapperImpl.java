package com.ducthong.TopCV.domain.mapper.impl;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.ducthong.TopCV.domain.dto.employer.AddEmployerRequestDTO;
import com.ducthong.TopCV.domain.dto.employer.EmployerResponseDTO;
import com.ducthong.TopCV.domain.dto.employer.UpdEmployerRequestDTO;
import com.ducthong.TopCV.domain.entity.account.Employer;
import com.ducthong.TopCV.domain.mapper.EmployerMapper;

@Component
public class EmployerMapperImpl implements EmployerMapper {
    /**
     * Converts an {@link Employer} entity to an {@link EmployerResponseDTO}.
     *
     * <p>This method maps the fields from the given {@code Employer} entity to a new
     * {@code EmployerResponseDTO} object. It includes all relevant fields such as
     * id, username, firstName, lastName, gender, dateOfBirth, email, avatar,
     * phoneNumber, address, lastUpdated, lastLogIn, whenCreated, whenDeleted, and
     * verifiedLevel.</p>
     *
     * @param entity the {@code Employer} entity to convert; must not be {@code null}
     * @return a new {@code EmployerResponseDTO} containing the data from the given entity
     * @throws IllegalArgumentException if the provided entity is {@code null}
     */
    @Override
    public EmployerResponseDTO toEmployerResponseDto(Employer entity) {
        return EmployerResponseDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .gender(entity.getGender())
                .dateOfBirth(entity.getDateOfBirth())
                .email(entity.getEmail())
                .avatar(entity.getAvatar())
                .phoneNumber(entity.getPhoneNumber())
                .address(entity.getAddress())
                .lastUpdated(entity.getLastUpdated())
                .lastLogIn(entity.getLastLogIn())
                .whenCreated(entity.getWhenCreated())
                .whenDeleted(entity.getWhenDeleted())
                .verifiedLevel(entity.getVerifiedLevel())
                .build();
    }

    /**
     * Converts an {@link AddEmployerRequestDTO} to an {@link Employer} entity
     * @return Employer entity
     */
    @Override
    public Employer addEmployerDtoToEmployerEntity(AddEmployerRequestDTO requestDTO) {
        return Employer.builder()
                .username(requestDTO.username())
                .password(requestDTO.password())
                .firstName(requestDTO.firstName())
                .lastName(requestDTO.lastName())
                .deleted(false)
                .gender(requestDTO.gender())
                .dateOfBirth(requestDTO.dateOfBirth())
                .email(requestDTO.email())
                .phoneNumber(requestDTO.phoneNumber())
                .whenCreated(new Date())
                .verifiedLevel(requestDTO.verifiedLevel() == null ? 0 : requestDTO.verifiedLevel())
                .lastLogIn(null)
                .lastUpdated(null)
                .whenDeleted(null)
                .address(null)
                .avatar(null)
                .build();
    }

    @Override
    public Employer updEmployerDtoToEmployerEntity(Employer employerEntity, UpdEmployerRequestDTO requestDTO) {
        employerEntity.setFirstName(requestDTO.firstName());
        employerEntity.setLastName(requestDTO.lastName());
        employerEntity.setGender(requestDTO.gender());
        employerEntity.setDateOfBirth(requestDTO.dateOfBirth());
        employerEntity.setEmail(requestDTO.email());
        employerEntity.setPhoneNumber(requestDTO.phoneNumber());
        employerEntity.setLastUpdated(new Date());
        employerEntity.setVerifiedLevel(requestDTO.verifiedLevel());

        return employerEntity;
    }
}
