package com.ducthong.TopCV.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ducthong.TopCV.constant.messages.ErrorMessage;
import com.ducthong.TopCV.constant.messages.SuccessMessage;
import com.ducthong.TopCV.constant.meta.MetaConstant;
import com.ducthong.TopCV.domain.dto.account.AccountResponseDTO;
import com.ducthong.TopCV.domain.dto.account.AddCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.account.CandidateResponseDTO;
import com.ducthong.TopCV.domain.dto.account.UpdCandidateRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.SortingDTO;
import com.ducthong.TopCV.domain.entity.Image;
import com.ducthong.TopCV.domain.entity.account.Account;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.domain.mapper.Account2Mapper;
import com.ducthong.TopCV.domain.mapper.AccountMapper;
import com.ducthong.TopCV.domain.mapper.AddressMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.AccountRepository;
import com.ducthong.TopCV.repository.CandidateRepository;
import com.ducthong.TopCV.repository.ImageRepository;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.AccountService;
import com.ducthong.TopCV.service.CloudinaryService;
import com.ducthong.TopCV.utility.MessageSourceUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    // Repository
    private final AccountRepository accountRepo;
    private final CandidateRepository candidateRepository;
    private final ImageRepository imageRepository;
    // Service
    private final CloudinaryService cloudinaryService;
    // Utility
    private final MessageSourceUtil messageUtil;
    // Mapper
    private final Account2Mapper account2Mapper;
    // Variable

    @Value("${cloudinary.folder.avatar}")
    private String folderAvatar;
    // CANDIDATE
    @Override
    @Transactional
    public Response<CandidateResponseDTO> getActiveCandidateAccount(Integer id) {
        Optional<Candidate> res = candidateRepository.findActiveAccountById(id);
        if (res.isEmpty()) throw new AppException(ErrorMessage.Account.NOT_FOUND);
        CandidateResponseDTO response = AccountMapper.INSTANCE.toCandidateResponseDto(res.get());
        return Response.successfulResponse(messageUtil.getMessage(SuccessMessage.Account.GET_ONE), response);
    }

    public Response<CandidateResponseDTO> addCandidateAccount(AddCandidateRequestDTO requestDTO) throws IOException {
        if (!accountRepo.findByUsername(requestDTO.username()).isEmpty())
            throw new AppException(ErrorMessage.Account.USERNAME_EXISTED, requestDTO.username());
        if (!accountRepo.findByEmail(requestDTO.email()).isEmpty())
            throw new AppException(ErrorMessage.Account.EMAIL_EXISTED, requestDTO.email());

        Candidate addCandidate = account2Mapper.addCandidateDtoToCandidateEntity(requestDTO);
        // Set Address
        addCandidate.setAddress(AddressMapper.INSTANCE.addRequestToPersonAddressEntity(requestDTO.address()));
        // Encode password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        addCandidate.setPassword(passwordEncoder.encode(requestDTO.password()));
        // Upload Avatar Image to Cloudinary
        Image avatarUpload;
        if (requestDTO.avatar() == null || requestDTO.avatar().isEmpty()) {
            Image defaultImage = imageRepository.findById(1).get();
            avatarUpload = Image.builder()
                    .name(defaultImage.getName())
                    .imageUrl(defaultImage.getImageUrl())
                    .imagePublicId(defaultImage.getImagePublicId())
                    .whenCreated(new Date())
                    .build();
        } else {
            Map uploadImage = cloudinaryService.upload(requestDTO.avatar(), folderAvatar);
            avatarUpload = Image.builder()
                    .name((String) uploadImage.get("original_filename"))
                    .imageUrl((String) uploadImage.get("url"))
                    .imagePublicId((String) uploadImage.get("public_id"))
                    .whenCreated(new Date())
                    .build();
        }
        addCandidate.setAvatar(avatarUpload);

        Candidate resCandidate = candidateRepository.save(addCandidate);

        return Response.successfulResponse(
                messageUtil.getMessage(SuccessMessage.Account.ADD),
                AccountMapper.INSTANCE.toCandidateResponseDto(resCandidate));
    }

    @Override
    public Response<CandidateResponseDTO> updCandidateAccount(Integer id, UpdCandidateRequestDTO requestDTO) {
        try {
            Candidate newCandidate = account2Mapper.updCandidateToCandidate(id, requestDTO);
            return Response.successfulResponse(
                    messageUtil.getMessage(SuccessMessage.Account.UPDATE),
                    AccountMapper.INSTANCE.toCandidateResponseDto(accountRepo.save(newCandidate)));
        } catch (Exception e) {
            throw new AppException(messageUtil.getMessage(ErrorMessage.Account.UPDATE));
        }
    }

    // ADMIN
    @Override
    @Transactional
    public MetaResponse<MetaResponseDTO, List<AccountResponseDTO>> getAllActiveAccount(MetaRequestDTO requestDTO) {
        Sort sort = requestDTO.sortDir().equals(MetaConstant.Sorting.DEFAULT_DIRECTION)
                ? Sort.by(requestDTO.sortField()).ascending()
                : Sort.by(requestDTO.sortField()).descending();
        Pageable pageable = PageRequest.of(requestDTO.currentPage(), requestDTO.pageSize(), sort);
        Page<Account> page = requestDTO.keyword() == null
                ? accountRepo.getAllActiveAccount(pageable)
                : accountRepo.searchAllActiveAccount(requestDTO.keyword(), pageable);
        List<AccountResponseDTO> li = AccountMapper.INSTANCE.toListAccountResponseDtos(page.getContent());
        if (li.isEmpty()) {
            throw new AppException(ErrorMessage.Account.EMPTY_LIST);
        }
        return MetaResponse.successfulResponse(
                messageUtil.getMessage(SuccessMessage.Account.GET_LIST),
                MetaResponseDTO.builder()
                        .totalItems((int) page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .currentPage(requestDTO.currentPage())
                        .pageSize(requestDTO.pageSize())
                        .sorting(SortingDTO.builder()
                                .sortField(requestDTO.sortField())
                                .sortDir(requestDTO.sortDir())
                                .build())
                        .build(),
                li);
        // return Response.successfulResponse(messageUtil.getMessage(SuccessMessage.Account.GET_LIST), li);
    }

    @Override
    @Transactional
    public Response<List<AccountResponseDTO>> getAllDeletedAccount() {
        List<AccountResponseDTO> li =
                AccountMapper.INSTANCE.toListAccountResponseDtos(accountRepo.getAllDeletedAccount());
        if (li.isEmpty()) {
            throw new RuntimeException(ErrorMessage.Account.EMPTY_LIST);
        }
        return Response.successfulResponse(messageUtil.getMessage(SuccessMessage.Account.GET_LIST), li);
    }

    @Override
    public Response<?> deleteTempAccount(Integer id) {
        Optional<Account> account = accountRepo.findById(id);
        if (account.isEmpty()) throw new AppException(ErrorMessage.Account.NOT_FOUND);
        account.get().setDeleted(true);
        account.get().setWhenDeleted(new Date());
        accountRepo.save(account.get());
        return Response.successfulResponse(messageUtil.getMessage(SuccessMessage.Account.DELETE_TEMP));
    }

    @Override
    public Response<AccountResponseDTO> retrieveAccount(Integer id) {
        Optional<Account> account = accountRepo.findById(id);
        if (account.isEmpty()) throw new AppException(ErrorMessage.Account.NOT_FOUND);
        account.get().setDeleted(false);
        accountRepo.save(account.get());
        return Response.successfulResponse(messageUtil.getMessage(SuccessMessage.Account.RETRIEVE));
    }

    @Override
    public Response<?> deletePermAccount(Integer id) {
        try {
            Optional<Account> account = accountRepo.findById(id);
            if (account.isEmpty()) throw new AppException(ErrorMessage.Account.NOT_FOUND);
            accountRepo.delete(account.get());
            // Delete Avatar in Cloudinary
            cloudinaryService.delete(account.get().getAvatar().getImagePublicId());
            return Response.successfulResponse(messageUtil.getMessage(SuccessMessage.Account.DELETE_PERM));
        } catch (AppException e) {
            throw new AppException(ErrorMessage.Account.DELETE_PERM);
        } catch (IOException e) {
            throw new AppException();
        }
    }
    //    public Response<CandidateResponseDTO> getDetailAccount(Integer id) {
    //        Optional<Candidate> res = candidateRepository.findById(id);
    //        if (res.isEmpty()) throw new AppException(ErrorMessage.Account.NOT_FOUND);
    //        CandidateResponseDTO response = AccountMapper.INSTANCE.toCandidateResponseDto(res.get());
    //        return Response.successfulResponse(messageUtil.getMessage(SuccessMessage.Account.GET_ONE), response);
    //    }
}
