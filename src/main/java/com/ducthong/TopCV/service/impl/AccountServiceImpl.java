package com.ducthong.TopCV.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ducthong.TopCV.constant.messages.ErrorMessage;
import com.ducthong.TopCV.constant.messages.SuccessMessage;
import com.ducthong.TopCV.domain.dto.account.AccountResponseDTO;
import com.ducthong.TopCV.domain.dto.account.CandidateResponseDTO;
import com.ducthong.TopCV.domain.entity.Candidate;
import com.ducthong.TopCV.domain.mapper.AccountMapper;
import com.ducthong.TopCV.repository.AccountRepository;
import com.ducthong.TopCV.repository.CandidateRepository;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.AccountService;
import com.ducthong.TopCV.utility.MessageSourceUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final MessageSourceUtil messageSourceUtil;
    private final AccountRepository accountRepository;
    private final CandidateRepository candidateRepository;

    @Override
    @Transactional
    public Response<List<AccountResponseDTO>> getAllActiveAccount() {
        List<AccountResponseDTO> li =
                AccountMapper.INSTANCE.toListAccountResponseDtos(accountRepository.getAllActiveAccount());
        if (li.isEmpty()) {
            throw new RuntimeException(ErrorMessage.Account.EMPTY_LIST);
        }
        return Response.successfulResponse(messageSourceUtil.getMessage(SuccessMessage.Account.GET_LIST), li);
    }

    @Override
    @Transactional
    public Response<CandidateResponseDTO> getCandidateAccount(Integer id) {
        Optional<Candidate> res = candidateRepository.findById(id);
        if (res.isEmpty()) throw new RuntimeException(ErrorMessage.Account.NOT_FOUND);
        CandidateResponseDTO response = AccountMapper.INSTANCE.toCandidateResponseDto(res.get());
        return Response.successfulResponse(messageSourceUtil.getMessage(SuccessMessage.Account.GET_ONE), response);
    }
}
