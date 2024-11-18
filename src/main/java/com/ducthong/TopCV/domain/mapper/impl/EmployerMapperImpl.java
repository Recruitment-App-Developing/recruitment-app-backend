package com.ducthong.TopCV.domain.mapper.impl;

import java.util.Map;

import com.ducthong.TopCV.domain.dto.employer.DetailEmployerResponseDTO;
import com.ducthong.TopCV.utility.TimeUtil;
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
                .dateOfBirth(TimeUtil.toStringDate(entity.getDateOfBirth()))
                .email(entity.getEmail())
                .avatar(Map.of(
                        "id", entity.getAvatar().getId().toString(),
                        "imageUrl", entity.getAvatar().getImageUrl()
                        ))
                .phoneNumber(entity.getPhoneNumber())
                .address(entity.getAddress())
                .lastUpdated(TimeUtil.toStringFullDateTime(entity.getLastUpdated()))
                .lastLogIn(entity.getLastLogIn())
                .whenCreated(TimeUtil.toStringFullDateTime(entity.getWhenCreated()))
                .whenDeleted(entity.getWhenDeleted())
                .verifiedLevel(entity.getVerifiedLevel())
                .build();
    }

    @Override
    public DetailEmployerResponseDTO toDetailEmployerResponseDto(Employer entity) {
        return DetailEmployerResponseDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .gender(entity.getGender())
                .dateOfBirth(TimeUtil.toStringDateTime(entity.getDateOfBirth()))
                .email(entity.getEmail())
                .avatar(Map.of(
                        "id", entity.getAvatar().getId().toString(),
                        "url", entity.getAvatar().getImageUrl()
                ))
                .phoneNumber(entity.getPhoneNumber())
                .address(entity.getAddress())
                .whenCreated(TimeUtil.toStringFullDateTime(entity.getWhenCreated()))
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
                .deleted(false)
                .gender(requestDTO.gender())
                .email(requestDTO.email())
                .phoneNumber(requestDTO.phoneNumber())
                .verifiedLevel(0)
                .whenCreated(TimeUtil.getDateTimeNow())
                .lastLogIn(null)
                .lastUpdated(null)
                .whenDeleted(null)
                .address(null)
                .avatar(null)
                .company(null)
                .build();
    }

    @Override
    public Employer updEmployerDtoToEmployerEntity(Employer employerEntity, UpdEmployerRequestDTO requestDTO) {
        employerEntity.setFirstName(requestDTO.firstName());
        employerEntity.setLastName(requestDTO.lastName());
        employerEntity.setGender(requestDTO.gender());
        employerEntity.setDateOfBirth(TimeUtil.convertToDate(requestDTO.dateOfBirth()));
        employerEntity.setLastUpdated(TimeUtil.getDateTimeNow());

        return employerEntity;
    }
}
