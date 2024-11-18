package com.ducthong.TopCV.domain.mapper;

import java.util.Date;
import java.util.List;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import com.ducthong.TopCV.domain.dto.account.AccountResponseDTO;
import com.ducthong.TopCV.domain.dto.account.AddCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.account.CandidateResponseDTO;
import com.ducthong.TopCV.domain.entity.account.Account;
import com.ducthong.TopCV.domain.entity.account.Candidate;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
    // ==================================================

    // ==================================================
    @Mapping(target = "avatar", ignore = true)
    @Mapping(source = "accountEntity", target = "fullName", qualifiedByName = "fullnameDto")
    AccountResponseDTO toAccountResponseDto(Account accountEntity);

    @Named("fullnameDto")
    default String locationToLocationDto(Account enity) {
        return enity.getFirstName() + " " + enity.getLastName();
    }

    @AfterMapping
    default void editAccountResponseDto(@MappingTarget AccountResponseDTO accountResponseDTO, Account accountEntity) {
        System.out.println("Ok");
        accountResponseDTO.setFullName(accountEntity.getFirstName() + " " + accountEntity.getLastName());
        accountResponseDTO.setAvatar(accountEntity.getAvatar().getImageUrl());
    }

    List<AccountResponseDTO> toListAccountResponseDtos(List<Account> listAccountEntity);

    // Detail Candidate================================
    // @Mapping(target = "avatar", source = "avatar")
    CandidateResponseDTO toCandidateResponseDto(Candidate entity);

    // ====================================================
    //    @Mapping(target = "password", ignore = true)
    //    @Mapping(target = "avatar", ignore = true)
    //    @Mapping(target = "address", ignore = true)
    //    Candidate addCandidateDtoToCandidateEntity(AddCandidateRequestDTO request);

}
