package com.ducthong.TopCV.domain.mapper.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ducthong.TopCV.domain.dto.account.AccountResponseDTO;
import com.ducthong.TopCV.domain.dto.account.AddCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.account.UpdCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.authentication.LoginResponseDTO;
import com.ducthong.TopCV.domain.entity.account.Account;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.mapper.Account2Mapper;
import com.ducthong.TopCV.repository.CandidateRepository;
import com.ducthong.TopCV.utility.JwtTokenUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Account2MapperImpl implements Account2Mapper {
    private final CandidateRepository candidateRepo;
    private final JwtTokenUtil jwtTokenUtil;
    // Variant
    @Value("${cloudinary.folder.default-avatar}")
    private String DEFAULT_AVATAR;

    @Override
    public Candidate addCandidateDtoToCandidateEntity(AddCandidateRequestDTO requestDTO) {
        Candidate newCandidate = new Candidate();
        newCandidate.setUsername(requestDTO.username());
        newCandidate.setFirstName(requestDTO.firstName());
        newCandidate.setLastName(requestDTO.lastName());
        newCandidate.setDeleted(false);
        newCandidate.setGender(requestDTO.gender());
        // newCandidate.setDateOfBirth(TimeUtil.convertToDateTime(requestDTO.dateOfBirth()));
        newCandidate.setEmail(requestDTO.email());
        newCandidate.setPhoneNumber(requestDTO.phoneNumber());
        // newCandidate.setLastUpdated(new Date());
        newCandidate.setLastUpdated(null);
        newCandidate.setLastLogIn(null);
        // newCandidate.setWhenCreated(new Date());
        newCandidate.setWhenDeleted(null);
        newCandidate.setIsFindJob(requestDTO.isFindJob());

        return newCandidate;
    }

    @Override
    public AccountResponseDTO toAccountResponseDto(Account account) {
        return AccountResponseDTO.builder()
                .id(account.getId())
                .username(account.getUsername())
                .fullName(account.getFirstName() + account.getLastName())
                .avatar(account.getAvatar().getImageUrl())
                .phoneNumber(account.getPhoneNumber())
                .email(account.getEmail())
                .build();
    }

    @Override
    public LoginResponseDTO toLoginResponseDto(Account account) {
        // Avatar
        String avatar = DEFAULT_AVATAR;
        if (account.getAvatar() != null) avatar = account.getAvatar().getImageUrl();
        // CV Profile Id
        String cvProfileId = null;
        if (account instanceof Candidate)
            cvProfileId = ((Candidate) account).getCvProfile().getId();

        return LoginResponseDTO.builder()
                .token(jwtTokenUtil.generateToken(account))
                .authenticated(true)
                .infor(AccountResponseDTO.builder()
                        .id(account.getId())
                        .username(account.getUsername())
                        .fullName(account.getFirstName() + " " + account.getLastName())
                        .avatar(avatar)
                        .phoneNumber(account.getPhoneNumber())
                        .email(account.getEmail())
                        .cvProfileId(cvProfileId)
                        .build())
                .build();
    }

    @Override
    public Candidate updCandidateToCandidate(Integer id, UpdCandidateRequestDTO requestDTO) {
        Candidate oldCandidate = candidateRepo.findById(id).get();
        oldCandidate.setFirstName(requestDTO.firstName());
        oldCandidate.setLastName(requestDTO.lastName());
        oldCandidate.setGender(requestDTO.gender());
        // oldCandidate.setDateOfBirth(requestDTO.dateOfBirth());
        oldCandidate.setPhoneNumber(requestDTO.phoneNumber());
        // oldCandidate.setLastUpdated(new Date());

        return oldCandidate;
    }
}
