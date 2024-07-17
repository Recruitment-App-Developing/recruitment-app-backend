package com.ducthong.TopCV.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ducthong.TopCV.domain.dto.account.UpdCandidateRequestDTO;
import com.ducthong.TopCV.domain.mapper.Account2Mapper;
import com.ducthong.TopCV.domain.mapper.AddressMapper;
import com.ducthong.TopCV.exceptions.AppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ducthong.TopCV.constant.messages.ErrorMessage;
import com.ducthong.TopCV.constant.messages.SuccessMessage;
import com.ducthong.TopCV.domain.dto.account.AccountResponseDTO;
import com.ducthong.TopCV.domain.dto.account.AddCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.account.CandidateResponseDTO;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.entity.Image;
import com.ducthong.TopCV.domain.mapper.AccountMapper;
import com.ducthong.TopCV.repository.AccountRepository;
import com.ducthong.TopCV.repository.CandidateRepository;
import com.ducthong.TopCV.repository.ImageRepository;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.AccountService;
import com.ducthong.TopCV.service.CloudinaryService;
import com.ducthong.TopCV.utility.MessageSourceUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final MessageSourceUtil messageUtil;
    private final AccountRepository accountRepository;
    private final CandidateRepository candidateRepository;
    private final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;
    private final Account2Mapper account2Mapper;

    @Value("${cloudinary.folder.avatar}")
    private String folderAvatar;
    // CANDIDATE
    @Override
    @Transactional
    public Response<CandidateResponseDTO> getNoDeletedCandidateAccount(Integer id) {
        Optional<Candidate> res = candidateRepository.findNoDeletedAccountById(id);
        if (res.isEmpty()) throw new RuntimeException(ErrorMessage.Account.NOT_FOUND);
        CandidateResponseDTO response = AccountMapper.INSTANCE.toCandidateResponseDto(res.get());
        return Response.successfulResponse(messageUtil.getMessage(SuccessMessage.Account.GET_ONE), response);
    }
    public Response<CandidateResponseDTO> addCandidateAccount(AddCandidateRequestDTO request)
            throws IOException {
        if (!accountRepository.findByUsername(request.username()).isEmpty()) throw new AppException(ErrorMessage.Account.USERNAME_EXISTED, request.username());
        if (!accountRepository.findByEmail(request.email()).isEmpty()) throw new AppException(ErrorMessage.Account.EMAIL_EXISTED, request.email());

        Candidate addCandidate = account2Mapper.addCandidateDtoToCandidateEntity(request);
        // Set Address
        addCandidate.setAddress(AddressMapper.INSTANCE.addRequestToPersonAddressEntity(request.address()));
        // Encode password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        addCandidate.setPassword(passwordEncoder.encode(request.password()));
        // Upload Avatar Image to Cloudinary
        Image temp;
        if (request.avatar() == null) {
            Image defaultImage = imageRepository.findById(1).get();
            temp = Image.builder()
                    .name(defaultImage.getName())
                    .imageUrl(defaultImage.getImageUrl())
                    .imagePublicId(defaultImage.getImagePublicId())
                    .whenCreated(new Date())
                    .build();
        } else {
            Map uploadImage = cloudinaryService.upload(request.avatar(), folderAvatar);
            temp = Image.builder()
                    .name((String) uploadImage.get("original_filename"))
                    .imageUrl((String) uploadImage.get("url"))
                    .imagePublicId((String) uploadImage.get("public_id"))
                    .whenCreated(new Date())
                    .build();
        }
        addCandidate.setAvatar(temp);

        Candidate resCandidate = candidateRepository.save(addCandidate);

        return Response.successfulResponse(
                messageUtil.getMessage(SuccessMessage.Account.ADD), AccountMapper.INSTANCE.toCandidateResponseDto(resCandidate));
    }
    @Override
    public Response<CandidateResponseDTO> updCandidateAccount(Integer id, UpdCandidateRequestDTO requestDTO) {
        try {
            Candidate newCandidate = account2Mapper.updCandidateToCandidate(id, requestDTO);
            return Response.successfulResponse(messageUtil.getMessage(SuccessMessage.Account.UPDATE),
                    AccountMapper.INSTANCE.toCandidateResponseDto(accountRepository.save(newCandidate)));
        } catch (Exception e) {
            throw new AppException(messageUtil.getMessage(ErrorMessage.Account.UPDATE));
        }
    }

    // ADMIN
    @Override
    @Transactional
    public Response<List<AccountResponseDTO>> getAllActiveAccount() {
        List<AccountResponseDTO> li =
                AccountMapper.INSTANCE.toListAccountResponseDtos(accountRepository.getAllActiveAccount());
        if (li.isEmpty()) {
            throw new RuntimeException(ErrorMessage.Account.EMPTY_LIST);
        }
        return Response.successfulResponse(messageUtil.getMessage(SuccessMessage.Account.GET_LIST), li);
    }
    @Override
    @Transactional
    public Response<List<AccountResponseDTO>> getAllDeletedAccount() {
        List<AccountResponseDTO> li =
                AccountMapper.INSTANCE.toListAccountResponseDtos(accountRepository.getAllDeletedAccount());
        if (li.isEmpty()) {
            throw new RuntimeException(ErrorMessage.Account.EMPTY_LIST);
        }
        return Response.successfulResponse(messageUtil.getMessage(SuccessMessage.Account.GET_LIST), li);
    }
//    public Response<CandidateResponseDTO> getDetailAccount(Integer id) {
//        Optional<Candidate> res = candidateRepository.findById(id);
//        if (res.isEmpty()) throw new AppException(ErrorMessage.Account.NOT_FOUND);
//        CandidateResponseDTO response = AccountMapper.INSTANCE.toCandidateResponseDto(res.get());
//        return Response.successfulResponse(messageUtil.getMessage(SuccessMessage.Account.GET_ONE), response);
//    }
}
