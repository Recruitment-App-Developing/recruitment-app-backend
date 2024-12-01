package com.ducthong.TopCV.service.impl;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.ducthong.TopCV.configuration.AppConfig;
import com.ducthong.TopCV.domain.dto.authentication.LoginResponseDTO;
import com.ducthong.TopCV.domain.dto.cloudinary.CloudinaryResponseDTO;
import com.ducthong.TopCV.domain.dto.employer.AddEmployerRequestDTO;
import com.ducthong.TopCV.domain.dto.employer.EmployerHomePageResponseDTO;
import com.ducthong.TopCV.domain.dto.employer.EmployerResponseDTO;
import com.ducthong.TopCV.domain.dto.employer.UpdEmployerRequestDTO;
import com.ducthong.TopCV.domain.entity.Company;
import com.ducthong.TopCV.domain.entity.Image;
import com.ducthong.TopCV.domain.entity.account.Employer;
import com.ducthong.TopCV.domain.entity.address.PersonAddress;
import com.ducthong.TopCV.domain.mapper.Account2Mapper;
import com.ducthong.TopCV.domain.mapper.AddressMapper;
import com.ducthong.TopCV.domain.mapper.EmployerMapper;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.repository.AccountRepository;
import com.ducthong.TopCV.repository.CompanyRepository;
import com.ducthong.TopCV.repository.EmployerRepository;
import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.service.CloudinaryService;
import com.ducthong.TopCV.service.EmployerService;
import com.ducthong.TopCV.utility.GetRoleUtil;
import com.ducthong.TopCV.utility.MessageSourceUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableTransactionManagement
public class EmployerServiceImpl implements EmployerService {
    // Repository
    private final AccountRepository accountRepo;
    private final EmployerRepository employerRepo;
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
    private final AppConfig appConfig;

    @Override
    public EmployerHomePageResponseDTO homePageForEmployer(Integer accountId) {
        return null; // TODO
    }

    @Override
    public Response<EmployerResponseDTO> getActiveEmployerAccount(Integer accountId) {
        Employer employer = GetRoleUtil.getEmployer(accountId);
        EmployerResponseDTO responseDTO = employerMapper.toEmployerResponseDto(employer);
        return Response.successfulResponse("", responseDTO);
    }

    @Override
    @Transactional
    public LoginResponseDTO registerEmployerAccount(AddEmployerRequestDTO requestDTO) throws IOException {
        if (!accountRepo.findByUsername(requestDTO.username()).isEmpty())
            throw new AppException("Tên đăng nhập này đã tồn tại");
        if (!accountRepo.findByEmail(requestDTO.email()).isEmpty()) throw new AppException("Email này đã tồn tại");

        Employer addEmployer = employerMapper.addEmployerDtoToEmployerEntity(requestDTO);
        // Person Address
        PersonAddress personAddress = new PersonAddress();
        addEmployer.setAddress(personAddress);
        // Set password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        addEmployer.setPassword(passwordEncoder.encode(requestDTO.password()));
        // Avatar
        Image avatar = new Image("Defaul Avatar", appConfig.getDEFAULT_AVATAR());
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
    @Transactional
    public Response<EmployerResponseDTO> updEmployerAccount(Integer accountId, UpdEmployerRequestDTO requestDTO)
            throws IOException {
        Employer oldEmployer = GetRoleUtil.getEmployer(accountId);
        Employer newEmployer = employerMapper.updEmployerDtoToEmployerEntity(oldEmployer, requestDTO);
        // Avatar
        if (requestDTO.avatar() != null && !requestDTO.avatar().equals("")) {
            Image avatar = oldEmployer.getAvatar();
            if (!Objects.equals(avatar.getImageUrl(), appConfig.getDEFAULT_AVATAR())) {
                cloudinaryService.delete(avatar.getImagePublicId());
            }
            CloudinaryResponseDTO uploadResponse =
                    cloudinaryService.uploadFileBase64_v2(requestDTO.avatar(), appConfig.getFOLDER_AVATAR());
            avatar.setName("The avatar of " + oldEmployer.getUsername());
            avatar.setImageUrl(uploadResponse.url());
            avatar.setImagePublicId(uploadResponse.public_id());
            newEmployer.setAvatar(avatar);
        }
        // Address
        PersonAddress oldPersonAddress = oldEmployer.getAddress();
        PersonAddress newPersonAddress = new PersonAddress();
        try {
            String[] address = requestDTO.address().split(";");
            if (oldPersonAddress != null
                    && (!Objects.equals(oldPersonAddress.getWardCode(), address[1])
                            || !Objects.equals(oldPersonAddress.getDetail(), address[0]))) {
                newPersonAddress = addressMapper.toPersonAddress(address[0], address[1]);
                newPersonAddress.setId(oldPersonAddress.getId());
                newEmployer.setAddress(newPersonAddress);
            }
        } catch (Exception e) {
            throw new AppException("Địa chỉ không hợp lệ");
        }
        try {
            return Response.successfulResponse(
                    "Cập nhật thông tin tài khoản thành công",
                    employerMapper.toEmployerResponseDto(employerRepo.save(newEmployer)));
        } catch (Exception e) {
            throw new AppException("Cập nhật thông tin cá nhân thất bại");
        }
    }

    @Override
    public void registerCompany(Integer accountId, Integer companyId) {
        Employer employer = GetRoleUtil.getEmployer(accountId);
        if (employer.getCompany() != null) throw new AppException("Tài khoản này đã được đăng kí công ty");

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
