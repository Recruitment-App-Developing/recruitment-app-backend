package com.ducthong.TopCV.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import com.ducthong.TopCV.domain.dto.account.AccountResponseDTO;
import com.ducthong.TopCV.domain.dto.authentication.LoginResponseDTO;
import com.ducthong.TopCV.domain.entity.Company;
import com.ducthong.TopCV.domain.mapper.Account2Mapper;
import com.ducthong.TopCV.repository.CompanyRepository;
import com.ducthong.TopCV.utility.GetRoleUtil;
import com.ducthong.TopCV.utility.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ducthong.TopCV.constant.messages.ErrorMessage;
import com.ducthong.TopCV.constant.messages.SuccessMessage;
import com.ducthong.TopCV.domain.dto.employer.AddEmployerRequestDTO;
import com.ducthong.TopCV.domain.dto.employer.EmployerResponseDTO;
import com.ducthong.TopCV.domain.dto.employer.UpdEmployerRequestDTO;
import com.ducthong.TopCV.domain.entity.Image;
import com.ducthong.TopCV.domain.entity.account.Employer;
import com.ducthong.TopCV.domain.mapper.AddressMapper;
import com.ducthong.TopCV.domain.mapper.EmployerMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.AccountRepository;
import com.ducthong.TopCV.repository.EmployerRepository;
import com.ducthong.TopCV.repository.ImageRepository;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.CloudinaryService;
import com.ducthong.TopCV.service.EmployerService;
import com.ducthong.TopCV.utility.MessageSourceUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@EnableTransactionManagement
public class EmployerServiceImpl implements EmployerService {
    // Repository
    private final AccountRepository accountRepo;
    private final EmployerRepository employerRepo;
    private final ImageRepository imageRepo;
    private final CompanyRepository companyRepo;
    // Service
    private final CloudinaryService cloudinaryService;
    // Utility
    private final MessageSourceUtil messageUtil;
    // Mapper
    private final Account2Mapper account2Mapper;
    private final EmployerMapper employerMapper;
    private final AddressMapper addressMapper;
    // Variable
    @Value("${cloudinary.folder.avatar}")
    private String folderAvatar;

    @Override
    public Response<EmployerResponseDTO> getActiveEmployerAccount(Integer id) {
        Optional<Employer> res = employerRepo.findActiveAccountById(id);
        if (res.isEmpty()) throw new AppException(ErrorMessage.Account.NOT_FOUND);
        EmployerResponseDTO responseDTO = employerMapper.toEmployerResponseDto(res.get());
        return Response.successfulResponse(messageUtil.getMessage(SuccessMessage.Account.GET_ONE), responseDTO);
    }

    @Override
    @Transactional
    public LoginResponseDTO registerEmployerAccount(AddEmployerRequestDTO requestDTO) throws IOException {
        if (!accountRepo.findByUsername(requestDTO.username()).isEmpty())
            throw new AppException("Tên đăng nhập này đã tồn tại");
        if (!accountRepo.findByEmail(requestDTO.email()).isEmpty())
            throw new AppException("Email này đã tồn tại");

        Employer addEmployer = employerMapper.addEmployerDtoToEmployerEntity(requestDTO);
        // Set Address
//        addEmployer.setAddress(addressMapper.addRequestToPersonAddressEntity(requestDTO.address()));
        // Set password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        addEmployer.setPassword(passwordEncoder.encode(requestDTO.password()));
        // Avatar
        Image avatar = new Image();
        addEmployer.setAvatar(avatar);
        // Upload Avatar Image to Cloudinary
//        Image avatarUpload;
//        if (requestDTO.avatar() == null || requestDTO.avatar().isEmpty()) {
//            Image defaultImage = imageRepo.findById(1).get();
//            avatarUpload = Image.builder()
//                    .name(defaultImage.getName())
//                    .imageUrl(defaultImage.getImageUrl())
//                    .imagePublicId(defaultImage.getImagePublicId())
//                    .whenCreated(new Date())
//                    .build();
//        } else {
//            Map uploadImage = cloudinaryService.upload(requestDTO.avatar(), folderAvatar);
//            avatarUpload = Image.builder()
//                    .name((String) uploadImage.get("original_filename"))
//                    .imageUrl((String) uploadImage.get("url"))
//                    .imagePublicId((String) uploadImage.get("public_id"))
//                    .whenCreated(new Date())
//                    .build();
//        }
//        addEmployer.setAvatar(avatarUpload);
        Employer employerSave = employerRepo.save(addEmployer);
        return account2Mapper.toLoginResponseDto(employerSave);
    }

    @Override
    public Response<EmployerResponseDTO> updEmployerAccount(Integer id, UpdEmployerRequestDTO requestDTO) {
        try {
            Optional<Employer> oldEmployer = employerRepo.findById(id);
            if (oldEmployer.isEmpty()) throw new AppException(ErrorMessage.Account.NOT_FOUND);
            Employer newEmployer = employerMapper.updEmployerDtoToEmployerEntity(oldEmployer.get(), requestDTO);
            return Response.successfulResponse(
                    messageUtil.getMessage(SuccessMessage.Account.UPDATE),
                    employerMapper.toEmployerResponseDto(employerRepo.save(newEmployer)));
        } catch (Exception e) {
            throw new AppException(ErrorMessage.Account.UPDATE);
        }
    }

    @Override
    public void registerCompany(Integer accountId, Integer companyId) {
        Employer employer = GetRoleUtil.getEmployer(accountId);
        if (employer.getCompany()!=null) throw new AppException("Tài khoản này đã được đăng kí công ty");

        Optional<Company> companyOptional = companyRepo.findById(companyId);
        if (companyOptional.isEmpty()) throw new AppException("Công ty này không tồn tại");

        employer.setCompany(companyOptional.get());
        try {
            employerRepo.save(employer);
        } catch (Exception e) {
            throw new AppException("Đăng kí công ty không thành công");
        }
    }
}
