package com.ducthong.TopCV.extract_data.service.impl;

import com.ducthong.TopCV.domain.entity.CvProfile.Education;
import com.ducthong.TopCV.domain.entity.CvProfile.Experience;
import com.ducthong.TopCV.extract_data.constant.ExtractDataConstant;
import com.ducthong.TopCV.extract_data.dto.CvInforDTO;
import com.ducthong.TopCV.extract_data.dto.DeepSeekResponse;
import com.ducthong.TopCV.extract_data.entity.Award;
import com.ducthong.TopCV.extract_data.entity.CvInfor;
import com.ducthong.TopCV.extract_data.repository.CvInforRepository;
import com.ducthong.TopCV.extract_data.service.ExtractDataService;
import com.ducthong.TopCV.utility.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExtractDataServiceImpl implements ExtractDataService {
    private static final Logger log = LogManager.getLogger(ExtractDataServiceImpl.class);

    // Repository
    private final CvInforRepository cvInforRepo;

    public String extractData(MultipartFile cvFile, String cvId) {
        String cvInfor = getTextFromPdf(cvFile);
        if (StringUtils.isNullOrEmpty(cvInfor)) return "Chuỗi rỗng";
        CvInforDTO cvInforResponse = callToDeepSeekService(cvInfor);
        CvInfor cvInforResult = convertToCv(cvInforResponse, cvId);
        cvInforResult.setCreator(AuthUtil.getRequestedUser().getId());
        try {
            cvInforRepo.save(cvInforResult);
        } catch (Exception e) {
            log.error("Error when save CvInfor");
        }

        return "Thành công";
    }

    private CvInfor convertToCv(CvInforDTO cvInforDTO, String cvId) {
        CvInfor cvInfor = new CvInfor();
        cvInfor.setCvId(cvId);
        cvInfor.setFullName(cvInforDTO.fullName);
        cvInfor.setEmail(cvInforDTO.getEmail());
        cvInfor.setDateOfBirth(TimeUtil.convertStringToDoB(cvInforDTO.dateOfBirth));
        cvInfor.setPhone(cvInforDTO.getPhone());
        cvInfor.setAddress(cvInforDTO.getAddress());
        cvInfor.setApplicationPostion(cvInforDTO.getPositionApply());
        cvInfor.setTechnicalSkills(ListUtils.listToString(cvInforDTO.getTechnicalSkills()));
        cvInfor.setSoftSkills(ListUtils.listToString(cvInforDTO.softSkills));

        List<Award> awardList = new ArrayList<>();
        if (!ListUtils.isNullOrEmpty(cvInforDTO.getAward())) {
            cvInforDTO.getAward().forEach(item -> {
                if (!CvInforDTO.Award.checkAllNull(item)) {
                    Award award = new Award();
                    award.setName(item.getName());
                    award.setTime(item.getTime());
                    award.setCvInfor(cvInfor);
                    awardList.add(award);
                }
            });
        }
        cvInfor.setAwards(awardList);

        List<Education> educationList = new ArrayList<>();
        if (!ListUtils.isNullOrEmpty(cvInforDTO.getEducation())) {
            cvInforDTO.getEducation().forEach(item -> {
                if (!CvInforDTO.Education.checkAllNull(item)) {
                    Education education = new Education();
//                    education.setSchool(item.getSchool());
//                    education.setIndustry(item.getIndustry());
//                    education.setTime(item.getTime());
//                    education.setDescription(item.getDescription());
                    education.setSchoolName(item.getSchool());
                    education.setMainIndustry(item.getIndustry());
                    education.setTimeStr(item.getTime());
                    education.setDetail(item.getDescription());
                    education.setCvInfor(cvInfor);
                    educationList.add(education);
                }
            });
        }
        cvInfor.setEducations(educationList);

        List<Experience> experienceList = new ArrayList<>();
        if (!ListUtils.isNullOrEmpty(cvInforDTO.getExperience())) {
            cvInforDTO.getExperience().forEach(item -> {
                if (!CvInforDTO.Experience.checkAllNull(item)) {
                    Experience experience = new Experience();
//                    experience.setCompany(item.getCompany());
//                    experience.setTime(item.getTime());
//                    experience.setPosition(item.getPosition());
//                    experience.setDescription(item.getDescription());
                    experience.setCompanyName(item.getCompany());
                    experience.setTimeStr(item.getTime());
                    experience.setPosition(item.getPosition());
                    experience.setDetail(item.getDescription());
                    experience.setCvInfor(cvInfor);
                    experienceList.add(experience);
                }
            });
        }
        cvInfor.setExperiences(experienceList);

        return cvInfor;
    }

    private CvInforDTO callToDeepSeekService(String cvInfor) {
        CvInforDTO cvInforResult = new CvInforDTO();
        try {
            RestTemplate restTemplate = new RestTemplate();

            String jsonBody = getJsonBody(cvInfor);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", ExtractDataConstant.API_KEY);

            HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<DeepSeekResponse> responseEntity = restTemplate.exchange(
                    ExtractDataConstant.API_URL,
                    HttpMethod.POST,
                    requestEntity,
                    DeepSeekResponse.class
            );

            ObjectMapper ob = new ObjectMapper();

            String cvInforFormat = formatCvInfor(responseEntity.getBody().getChoices().get(0).getMessage().getContent());
            cvInforResult = ob.readValue(cvInforFormat, CvInforDTO.class);
            log.info(cvInforResult);
            return cvInforResult;
        } catch (Exception e) {
            log.error("Lỗi khi extract data from cv");
            return cvInforResult;
        }
    }
    private String getTextFromPdf(MultipartFile cvFile) {
        String result = "";
        try (PDDocument document = PDDocument.load(cvFile.getInputStream())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            result = pdfStripper.getText(document);

            System.out.println("Nội dung PDF:");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    private String getJsonBody(String cvInfor) throws JsonProcessingException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", ExtractDataConstant.MODEL);

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> message = new HashMap<>();
        message.put("role", "user");

        String content = cvInfor + "\n\n" + ExtractDataConstant.EXTRACT_INSTRUCTION;
        message.put("content", content);
        messages.add(message);

        requestBody.put("messages", messages);
        String jsonBody = Common.toJsonString(requestBody);
        return jsonBody;
    }
    private String formatCvInfor(String text) {
        String jsonString = text.replace("```json\n", "") // Loại bỏ phần thừa
                .replace("```", "") // Loại bỏ ký hiệu markdown
                .trim();
        return jsonString;
    }
}
