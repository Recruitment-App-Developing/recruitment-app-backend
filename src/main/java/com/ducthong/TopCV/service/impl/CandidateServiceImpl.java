package com.ducthong.TopCV.service.impl;

import com.ducthong.TopCV.domain.dto.account.AccountResponseDTO;
import com.ducthong.TopCV.domain.dto.account.candidate.CandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.authentication.LoginResponseDTO;
import com.ducthong.TopCV.domain.entity.CvProfile.CvProfile;
import com.ducthong.TopCV.domain.entity.Image;
import com.ducthong.TopCV.domain.entity.account.Account;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.mapper.CandidateMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.AccountRepository;
import com.ducthong.TopCV.repository.CandidateRepository;
import com.ducthong.TopCV.service.CandidateService;
import com.ducthong.TopCV.utility.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    // Repository
    private final AccountRepository accountRepo;
    private final CandidateRepository candidateRepo;
    // Mapper
    private final CandidateMapper candidateMapper;
    // Util
    private final JwtTokenUtil jwtTokenUtil;
    @Override
    public LoginResponseDTO registerCandidate(CandidateRequestDTO requestDTO) {
        // Check username
        Optional<Account> checkUsername = accountRepo.findByUsername(requestDTO.username());
        if (checkUsername.isPresent()) throw new AppException("This username is existed");
        // Check email
        Optional<Account> checkEmail = accountRepo.findByEmail(requestDTO.email());
        if (checkEmail.isPresent()) throw new AppException("This email is existed");
        // Check phone

        //Candidate newCandidate = candidateMapper.candidateRequestDtoToCandidate(requestDTO);
        Candidate newCandidate = new Candidate();
        newCandidate.setUsername(requestDTO.username());
        newCandidate.setEmail(requestDTO.email());
        newCandidate.setWhenCreated(new Date());
        newCandidate.setDeleted(false);
        newCandidate.setIsFindJob(false);
        // Password
        // Encode password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        newCandidate.setPassword(passwordEncoder.encode(requestDTO.password()));
        // Image
        Image newAvatar = new Image();
        newCandidate.setAvatar(newAvatar);
        // Cv Profile
        CvProfile newCvProfile = new CvProfile();
        newCandidate.setCvProfile(newCvProfile);

        Candidate saveCandidate = candidateRepo.save(newCandidate);
        return LoginResponseDTO.builder()
                .token(jwtTokenUtil.generateToken(saveCandidate))
                .authenticated(true)
                .infor(AccountResponseDTO.builder()
                        .id(saveCandidate.getId())
                        .username(saveCandidate.getUsername())
                        .fullName(saveCandidate.getFirstName() + " "+ saveCandidate.getLastName())
                        .avatar(saveCandidate.getAvatar().getImageUrl())
                        .phoneNumber(saveCandidate.getPhoneNumber())
                        .email(saveCandidate.getEmail())
                        .build())
                .build();
    }
}
