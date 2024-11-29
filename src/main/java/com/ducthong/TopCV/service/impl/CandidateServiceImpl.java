package com.ducthong.TopCV.service.impl;

import com.ducthong.TopCV.configuration.AppConfig;
import com.ducthong.TopCV.domain.dto.account.AccountResponseDTO;
import com.ducthong.TopCV.domain.dto.account.UpdCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.account.candidate.CandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.authentication.LoginResponseDTO;
import com.ducthong.TopCV.domain.dto.candidate.DetailCandidateResponseDTO;
import com.ducthong.TopCV.domain.dto.cloudinary.CloudinaryResponseDTO;
import com.ducthong.TopCV.domain.entity.CvProfile.CvProfile;
import com.ducthong.TopCV.domain.entity.Image;
import com.ducthong.TopCV.domain.entity.account.Account;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.entity.address.PersonAddress;
import com.ducthong.TopCV.domain.mapper.AddressMapper;
import com.ducthong.TopCV.domain.mapper.CandidateMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.AccountRepository;
import com.ducthong.TopCV.repository.CandidateRepository;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.CandidateService;
import com.ducthong.TopCV.service.CloudinaryService;
import com.ducthong.TopCV.utility.GetRoleUtil;
import com.ducthong.TopCV.utility.JwtTokenUtil;
import com.ducthong.TopCV.utility.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@EnableTransactionManagement
public class CandidateServiceImpl implements CandidateService {
    // Repository
    private final AccountRepository accountRepo;
    private final CandidateRepository candidateRepo;
    // Service
    private final CloudinaryService cloudinaryService;
    // Mapper
    private final CandidateMapper candidateMapper;
    private final AddressMapper addressMapper;
    // Util
    private final JwtTokenUtil jwtTokenUtil;
    private final AppConfig appConfig;

    @Override
    @Transactional
    public DetailCandidateResponseDTO getDetailCandidate(Integer accountId) {
        try {
            Candidate candidate = GetRoleUtil.getCandidate(accountId);
            return candidateMapper.toDetailCandidateResponseDto(candidate);
        } catch (Exception e){
            e.printStackTrace();
            throw new AppException("Lỗi");
        }
    }

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
        newCandidate.setWhenCreated(TimeUtil.getDateTimeNow());
        newCandidate.setDeleted(false);
        newCandidate.setIsFindJob(false);
        // Password
        // Encode password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        newCandidate.setPassword(passwordEncoder.encode(requestDTO.password()));
        // Image
        Image avatar = new Image("Defaul Avatar", appConfig.getDEFAULT_AVATAR());
        newCandidate.setAvatar(avatar);
        // Person Address
        PersonAddress address = new PersonAddress();
        newCandidate.setAddress(address);
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

    @Override
    @Transactional
    public DetailCandidateResponseDTO updateCandidate(Integer accountId, UpdCandidateRequestDTO requestDTO) throws IOException {
        Candidate oldCandidate = GetRoleUtil.getCandidate(accountId);
        Candidate newCandidate = candidateMapper.udpCandidateDtoToCandidate(oldCandidate, requestDTO);
        //Avatar
        if (requestDTO.avatar() != null && !requestDTO.avatar().equals("")) {
            Image avatar = oldCandidate.getAvatar();
            if (!Objects.equals(avatar.getImageUrl(), appConfig.getDEFAULT_AVATAR())) {
                try {
                    cloudinaryService.delete(avatar.getImagePublicId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            CloudinaryResponseDTO uploadResponse = cloudinaryService.uploadFileBase64_v2(requestDTO.avatar(), appConfig.getFOLDER_AVATAR());
            avatar.setName("The avatar of "+oldCandidate.getUsername());
            avatar.setImageUrl(uploadResponse.url());
            avatar.setImagePublicId(uploadResponse.public_id());
            newCandidate.setAvatar(avatar);
        }
        // Address
        PersonAddress oldPersonAddress = oldCandidate.getAddress();
        PersonAddress newPersonAddress = new PersonAddress();
        try {
            String[] address = requestDTO.address().split(";");
            if (oldPersonAddress != null &&
                    (!Objects.equals(oldPersonAddress.getWardCode(), address[1])
                            || !Objects.equals(oldPersonAddress.getDetail(), address[0]))){
                newPersonAddress = addressMapper.toPersonAddress(address[0], address[1]);
                newPersonAddress.setId(oldPersonAddress.getId());
                newCandidate.setAddress(newPersonAddress);
            }
        } catch (Exception e) {
            throw new AppException("Địa chỉ không hợp lệ");
        }
        try{
            return candidateMapper.toDetailCandidateResponseDto(candidateRepo.save(newCandidate));
        } catch (Exception e) {
            throw new AppException("Cập nhật thông tin cá nhân thất bại");
        }
    }
}
