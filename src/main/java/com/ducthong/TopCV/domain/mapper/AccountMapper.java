package com.ducthong.TopCV.domain.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.ducthong.TopCV.domain.dto.account.AccountResponseDTO;
import com.ducthong.TopCV.domain.dto.account.CandidateResponseDTO;
import com.ducthong.TopCV.domain.entity.Account;
import com.ducthong.TopCV.domain.entity.Candidate;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
    // ==================================================

    // ==================================================
    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    AccountResponseDTO toAccountResponseDto(Account accountEntity);

    List<AccountResponseDTO> toListAccountResponseDtos(List<Account> listAccountEntity);

    @AfterMapping
    default void editAccountResponseDto(@MappingTarget AccountResponseDTO accountResponseDTO, Account accountEntity) {
        accountResponseDTO.setFullName(accountEntity.getFirstName() + " " + accountEntity.getLastName());
        accountResponseDTO.setAvatar(accountEntity.getAvatar().getUrlImg());
    }

    // Detail Candidate================================
    // @Mapping(target = "avatar", source = "avatar")
    CandidateResponseDTO toCandidateResponseDto(Candidate entity);

    // ====================================================
    //    @Mapping(target = "avatar", ignore = true)
    //    Candidate addCandidateDtoToCandidateEntity(AddCandidateRequestDTO request);
    //    @AfterMapping
    //    default void editAddCandidateReuqestDto(@MappingTarget Candidate candidateEntity, AddCandidateRequestDTO
    // request){
    //        candidateEntity.setDeleted(false);
    //        candidateEntity.setAvatar(ImageMapper.INSTANCE.toImageEntity(new ImageRequestDTO(request.avatar())));
    //        candidateEntity.setAddress(null);
    //        candidateEntity.setLastUpdated(null);
    //        candidateEntity.setLastLogIn(null);
    //        candidateEntity.setWhenCreated(new Date());
    //        candidateEntity.setWhenDeleted(null);
    //    }
}
