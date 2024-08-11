package com.ducthong.TopCV.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ducthong.TopCV.constant.messages.ErrorMessage;
import com.ducthong.TopCV.constant.messages.SuccessMessage;
import com.ducthong.TopCV.constant.meta.MetaConstant;
import com.ducthong.TopCV.domain.dto.industry.IndustryRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.SortingDTO;
import com.ducthong.TopCV.domain.entity.Industry;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.IndustryRepository;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.IndustryService;
import com.ducthong.TopCV.utility.MessageSourceUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndustryServiceImpl implements IndustryService {
    // Repository
    private final IndustryRepository industryRepo;
    // Util
    private final MessageSourceUtil messageUtil;

    @Override
    public MetaResponse<MetaResponseDTO, List<Industry>> getAllActiveIndustry(MetaRequestDTO requestDTO) {
        Sort sort = requestDTO.sortDir().equals(MetaConstant.Sorting.DEFAULT_DIRECTION)
                ? Sort.by(requestDTO.sortField()).ascending()
                : Sort.by(requestDTO.sortField()).descending();
        Pageable pageable = PageRequest.of(requestDTO.currentPage(), requestDTO.pageSize(), sort);
        Page<Industry> page = requestDTO.keyword() == null
                ? industryRepo.getAllActiveIndustry(pageable)
                : industryRepo.searchActiveIndustry(requestDTO.keyword(), pageable);
        List<Industry> li = page.getContent();
        if (li.isEmpty()) throw new AppException(messageUtil.getMessage(ErrorMessage.Industry.EMPTY_LIST));
        return MetaResponse.successfulResponse(
                messageUtil.getMessage(SuccessMessage.Industry.GET_LIST, page.getTotalElements()),
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
    }

    @Override
    public MetaResponse<MetaResponseDTO, List<Industry>> getAllIndustry(MetaRequestDTO requestDTO) {
        Sort sort = requestDTO.sortDir().equals(MetaConstant.Sorting.DEFAULT_DIRECTION)
                ? Sort.by(requestDTO.sortField()).ascending()
                : Sort.by(requestDTO.sortField()).descending();
        Pageable pageable = PageRequest.of(requestDTO.currentPage(), requestDTO.pageSize(), sort);
        Page<Industry> page = requestDTO.keyword() == null
                ? industryRepo.getAllIndustry(pageable)
                : industryRepo.searchAllIndustry(requestDTO.keyword(), pageable);
        List<Industry> li = page.getContent();
        if (li.isEmpty()) throw new AppException(messageUtil.getMessage(ErrorMessage.Industry.EMPTY_LIST));
        return MetaResponse.successfulResponse(
                messageUtil.getMessage(SuccessMessage.Industry.GET_LIST, page.getTotalElements()),
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
    }

    @Override
    public Response<Industry> getDetailActiveIndustry(Integer id) {
        Optional<Industry> industry = industryRepo.findActiveIndustryById(id);
        if (industry.isEmpty()) throw new AppException(ErrorMessage.Industry.NOT_FOUND);
        return Response.successfulResponse(messageUtil.getMessage(SuccessMessage.Industry.GET_DETAIL), industry.get());
    }

    @Override
    public Response<Industry> getDetailAllIndustry(Integer id) {
        Optional<Industry> industry = industryRepo.findById(id);
        if (industry.isEmpty()) throw new AppException(ErrorMessage.Industry.NOT_FOUND);
        return Response.successfulResponse(messageUtil.getMessage(SuccessMessage.Industry.GET_DETAIL), industry.get());
    }

    @Override
    public Response<Industry> addIndustry(IndustryRequestDTO requestDTO) {
        if (!industryRepo.findIndustryByName(requestDTO.name()).isEmpty())
            throw new AppException(ErrorMessage.Industry.EXISTED);
        try {
            Industry newIndustry = Industry.builder()
                    .name(requestDTO.name())
                    .description(requestDTO.description())
                    .whenCreated(new Date())
                    .lastUpdated(null)
                    .deleted(false)
                    .whenDeleted(null)
                    .build();
            Industry res = industryRepo.save(newIndustry);
            return Response.successfulResponse(SuccessMessage.Industry.ADD_ONE, res);
        } catch (Exception e) {
            throw new AppException(ErrorMessage.Industry.ADD_ONE);
        }
    }

    @Override
    public Response<Industry> updIndustry(Integer id, IndustryRequestDTO requestDTO) {
        if (!industryRepo.findIndustryByName(requestDTO.name()).isEmpty())
            throw new AppException(ErrorMessage.Industry.EXISTED);
        Optional<Industry> updIndustry = industryRepo.findById(id);
        if (updIndustry.isEmpty()) throw new AppException(ErrorMessage.Industry.NOT_FOUND);
        updIndustry.get().setName(requestDTO.name());
        updIndustry.get().setDescription(requestDTO.description());
        updIndustry.get().setLastUpdated(new Date());
        Industry res = industryRepo.save(updIndustry.get());
        return Response.successfulResponse(SuccessMessage.Industry.UPDATE_ONE, res);
    }

    @Override
    public Response<?> deleteTempIndustry(Integer id) {
        return null;
    }

    @Override
    public Response<?> deletePermIndustry(Integer id) {
        return null;
    }
}
