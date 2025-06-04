package com.ducthong.TopCV.extract_data.service.impl;

import com.ducthong.TopCV.domain.entity.CvProfile.Education;
import com.ducthong.TopCV.domain.entity.CvProfile.Experience;
import com.ducthong.TopCV.extract_data.constant.ExtractDataConstant;
import com.ducthong.TopCV.extract_data.dto.CvInforDTO;
import com.ducthong.TopCV.extract_data.dto.DeepSeekResponse;
import com.ducthong.TopCV.extract_data.entity.Award;
import com.ducthong.TopCV.extract_data.entity.CvInfor;
import com.ducthong.TopCV.extract_data.repository.CvInforRepository;
import com.ducthong.TopCV.extract_data.service.CvTrackingService;
import com.ducthong.TopCV.extract_data.service.ExtractDataService;
import com.ducthong.TopCV.extract_data.service.WebSocketService;
import com.ducthong.TopCV.utility.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ExtractDataServiceImpl implements ExtractDataService {
    private static final Logger log = LogManager.getLogger(ExtractDataServiceImpl.class);

    // Repository
    private final CvInforRepository cvInforRepo;
    // Service
    private final CvTrackingService trackingService;
    private final WebSocketService webSocketService;

    @Async
    public void extractData(MultipartFile cvFile, String cvId) {
        String cvInfor = getTextFromPdf(cvFile);
        if (StringUtils.isNullOrEmpty(cvInfor)) log.error("Chuỗi rỗng");
        CvInforDTO cvInforResponse = callToDeepSeekService(cvInfor);
        CvInfor cvInforResult = convertToCv(cvInforResponse, cvId);
        cvInforResult.setCreator(AuthUtil.getRequestedUser().getId());
        try {
            log.info("[EXTRACT_DATA_SERVICE] Save cv infor with cvId: {}", cvId);
//            cvInforRepo.save(cvInforResult);
        } catch (Exception e) {
            log.error("Error when save CvInfor");
        }

        log.info("Thành công");
    }

    @Async
    public CompletableFuture<Void> extractDataWithWebSocket(MultipartFile file, String cvId, String sessionId) {
        try {
            String content = getTextFromPdf(file);
            if (content == null || content.trim().isEmpty()) {
                throw new IllegalStateException("PDF rỗng");
            }

            CvInforDTO dto = callToDeepSeekService(content);
            CvInfor cv = convertToCv(dto, cvId);
            cv.setCreator(AuthUtil.getRequestedUser().getId());
            Thread.sleep(2000);
            cvInforRepo.save(cv);

            int done = trackingService.increment(sessionId);
            int total = trackingService.getTotal(sessionId);
            webSocketService.sendProgress(cvId, "DONE", sessionId, total, done);

            if (done == total) {
                trackingService.clear(sessionId);
            }

        } catch (Exception e) {
            int done = trackingService.increment(sessionId);
            webSocketService.sendProgress(cvId, "FAILED", sessionId,
                    trackingService.getTotal(sessionId), done);
        }

        return CompletableFuture.completedFuture(null);
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
            log.info("[DEEPSEEK_SERVICE] Data from deepseek: {}", Common.toJsonString(responseEntity));
            ObjectMapper ob = new ObjectMapper();
            String cvInforFormat = formatCvInfor(responseEntity.getBody().getChoices().get(0).getMessage().getContent());
            cvInforResult = ob.readValue(cvInforFormat, CvInforDTO.class);
            return cvInforResult;
        } catch (HttpClientErrorException e) {
            log.error("[DEEPSEEK_SERVICE] Client error while calling DeepSeek API: {}", e.getResponseBodyAsString(), e);
        } catch (HttpServerErrorException e) {
            log.error("[DEEPSEEK_SERVICE] Server error while calling DeepSeek API: {}", e.getResponseBodyAsString(), e);
        } catch (RestClientException e) {
            log.error("[DEEPSEEK_SERVICE] RestClientException when calling DeepSeek API", e);
        } catch (JsonProcessingException e) {
            log.error("[DEEPSEEK_SERVICE] Error processing JSON response from DeepSeek", e);
        } catch (Exception e) {
            log.error("[DEEPSEEK_SERVICE] Unexpected error in DeepSeek service", e);
        }
        return cvInforResult;
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
