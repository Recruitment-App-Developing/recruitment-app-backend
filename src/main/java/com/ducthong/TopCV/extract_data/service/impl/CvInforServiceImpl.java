package com.ducthong.TopCV.extract_data.service.impl;

import com.ducthong.TopCV.domain.entity.CV;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.extract_data.dto.CvInforDTO;
import com.ducthong.TopCV.extract_data.dto.CvInforItem;
import com.ducthong.TopCV.extract_data.dto.DetailCvInforDTO;
import com.ducthong.TopCV.extract_data.entity.CvInfor;
import com.ducthong.TopCV.extract_data.repository.CvInforRepository;
import com.ducthong.TopCV.extract_data.repository.CvInforRepositoryCustom;
import com.ducthong.TopCV.extract_data.service.CvInforService;
import com.ducthong.TopCV.repository.CvRepository;
import com.ducthong.TopCV.utility.TimeUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CvInforServiceImpl implements CvInforService {
    private final CvInforRepository cvInforRepo;
    private final CvRepository cvRepo;
    private final CvInforRepositoryCustom cvInforRepoCustom;

    @Override
    public List<CvInforItem> getList() {
        return cvInforRepoCustom.getListCvInfor();
    }

    @Override
    @Transactional
    public DetailCvInforDTO getDetail(String cvInforId) {
        Optional<CvInfor> cvInforOpt = cvInforRepo.findById(cvInforId);
        if (cvInforOpt.isEmpty()) throw new AppException("Không tìm thấy thông tin của CV");

        CvInfor cvInfor = cvInforOpt.get();
        Optional<CV> cvOpt = cvRepo.findById(cvInfor.getCvId());
        if (cvOpt.isEmpty()) throw new AppException("Không tìm thấy CV");

        DetailCvInforDTO response = new DetailCvInforDTO();
        // Cv
        response.setCvId(cvOpt.get().getId());
        response.setCvName(cvOpt.get().getName());
        // General infor
        response.setCvInforId(cvInforId);
        response.setFullName(cvInfor.getFullName());
        response.setEmail(cvInfor.getEmail());
        response.setDob(TimeUtil.toStringDate(cvInfor.getDateOfBirth()));
        response.setPhone(cvInfor.getPhone());
        response.setAddress(cvInfor.getAddress());
        response.setApplicationPosition(cvInfor.getApplicationPostion());
        // Award
        List<DetailCvInforDTO.Award> awards = new ArrayList<>();
        cvInfor.getAwards().forEach(award -> {
            DetailCvInforDTO.Award temp = new DetailCvInforDTO.Award();
            temp.setId(award.getId());
            temp.setName(award.getName());
            temp.setTimeStr(award.getTime());

            awards.add(temp);
        });
        response.setAwards(awards);
        // Education
        List<DetailCvInforDTO.Education> educations = new ArrayList<>();
        cvInfor.getEducations().forEach(education -> {
            DetailCvInforDTO.Education temp = new DetailCvInforDTO.Education();
            temp.setId(education.getId());
            temp.setSchoolName(education.getSchoolName());
            temp.setTimeStr(education.getTimeStr());
            temp.setDetail(education.getDetail());

            educations.add(temp);
        });
        response.setEducations(educations);
        // Experience
        List<DetailCvInforDTO.Experience> experiences = new ArrayList<>();
        cvInfor.getExperiences().forEach(experience -> {
            DetailCvInforDTO.Experience temp = new DetailCvInforDTO.Experience();
            temp.setId(experience.getId());
            temp.setCompanyName(experience.getCompanyName());
            temp.setPosition(experience.getPosition());
            temp.setTimeStr(experience.getTimeStr());
            temp.setDetail(experience.getDetail());

            experiences.add(temp);
        });
        response.setExperiences(experiences);
        return response;
    }
}
