package com.ducthong.TopCV.domain.mapper.impl;

import com.ducthong.TopCV.domain.dto.account.AddCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.account.UpdCandidateRequestDTO;
import com.ducthong.TopCV.domain.entity.account.Account;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.mapper.Account2Mapper;
import com.ducthong.TopCV.repository.AccountRepository;
import com.ducthong.TopCV.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class Account2MapperImpl implements Account2Mapper {
    private final CandidateRepository candidateRepo;
    @Override
    public Candidate updCandidateToCandidate(Integer id ,UpdCandidateRequestDTO requestDTO) {
        Candidate oldCandidate = candidateRepo.findById(id).get();
        oldCandidate.setFirstName(requestDTO.firstName());
        oldCandidate.setLastName(requestDTO.lastName());
        oldCandidate.setGender(requestDTO.gender());
        oldCandidate.setDateOfBirth(requestDTO.dateOfBirth());
        oldCandidate.setEmail(requestDTO.email());
        oldCandidate.setPhoneNumber(requestDTO.phoneNumber());
        oldCandidate.setLastUpdated(new Date());
        oldCandidate.setIsFindJob(requestDTO.isFindJob());

        return oldCandidate;
    }
    @Override
    public Candidate addCandidateDtoToCandidateEntity(AddCandidateRequestDTO requestDTO) {
        Candidate newCandidate = new Candidate();
        newCandidate.setUsername(requestDTO.username());
        newCandidate.setFirstName(requestDTO.firstName());
        newCandidate.setLastName(requestDTO.lastName());
        newCandidate.setDeleted(false);
        newCandidate.setGender(requestDTO.gender());
        newCandidate.setDateOfBirth(requestDTO.dateOfBirth());
        newCandidate.setEmail(requestDTO.email());
        newCandidate.setPhoneNumber(requestDTO.phoneNumber());
        newCandidate.setLastUpdated(new Date());
        newCandidate.setLastUpdated(null);
        newCandidate.setLastLogIn(null);
        newCandidate.setWhenCreated(new Date());
        newCandidate.setWhenDeleted(null);
        newCandidate.setIsFindJob(requestDTO.isFindJob());

        return newCandidate;
    }
}
