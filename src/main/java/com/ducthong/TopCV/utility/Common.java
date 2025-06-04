package com.ducthong.TopCV.utility;


import com.ducthong.TopCV.constant.Constants;
import com.ducthong.TopCV.constant.MoneyConstant;
import com.ducthong.TopCV.domain.dto.job.ProcessSalaryRequest;
import com.ducthong.TopCV.exceptions.AppException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Query;
import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Log4j2
public class Common {
    public static String toJsonString(Object object) {
        ObjectMapper ob = new ObjectMapper();
        try {
            return ob.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("[ERROR_COMMON] Error when write Object");
            return null;
        }
    }
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
    public static String arrayToString(String[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    public static String[] stringToArray(String str) {
        try {
            String[] result = str.split(",");
            return Arrays.stream(result).map(String::trim).toArray(String[]::new);
        } catch (Exception e) {
            log.error("Error when split array");
            return null;
        }
    }
    public static String generateId() {
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        return timestamp + "_" + uuid;
    }

    public static String extractFirstPageAsBase64(String path){
        String base64 = "";
        Path filePath = Paths.get(path);
        InputStream inputStream = null;
        try {
            inputStream = Files.newInputStream(filePath);
        } catch (IOException e) {
            log.error("[ERROR_CV_RESPONSE] error reading file {}", e.getMessage());
        }

        if (inputStream == null) {
            log.error("[ERROR_CV_RESPONSE] error reading file");
            return null;
        }

        try (PDDocument document = PDDocument.load(inputStream)) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            BufferedImage image = pdfRenderer.renderImageWithDPI(0, 150);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);

            byte[] imageBytes = baos.toByteArray();
            return Constants.BASE64_PREFIX + Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            log.error("[ERROR_CV_RESPONSE] error convert first page {}", e.getMessage());
            return null;
        }
    }

    public static String convertToSalry(ProcessSalaryRequest request) {
        String res = null;
        String salaryTo = convertToVndMoneyString(request.getSalaryTo());
        String salaryFrom = convertToVndMoneyString(request.getSalaryFrom());

        switch (request.getSalaryType()) {
            case MoneyConstant.TYPE_ABOUT: {
                res = "Khoảng " + salaryFrom + " - " + salaryTo; break;
            }
            case MoneyConstant.TYPE_FROM: {
                res = "Từ " + salaryFrom; break;
            }
            case MoneyConstant.TYPE_TO: {
                res = "Đến " + salaryTo; break;
            }
            case MoneyConstant.TYPE_EXACT: {
                res = salaryFrom; break;
            }
            default: {
                log.error("[ERROR_CONVERT_TO] error convert salary");
                throw new AppException("Tiền lương không đúng định dạng");
            }
        }

        switch (request.getSalaryUnit()) {
            case MoneyConstant.UNIT_VND: {
                res += " " + MoneyConstant.UNIT_VND_VI; break;
            }
            default: {
                res += " " + MoneyConstant.UNIT_USD; break;
            }
        }
        return res;
    }

    public static String convertToVndMoneyString(Integer money) {
        if (money == null) return null;
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(money);
    }

    public static void saveBase64Image(String base64Image, String folderPath, String fileName, String type) throws IOException {
        Path bannerFolder = Paths.get(folderPath);

        String[] parts = base64Image.split(",");
        String data = parts.length > 1 ? parts[1] : parts[0];
        byte[] imageBytes = Base64.getDecoder().decode(data);

        Path outputPath = bannerFolder.resolve(fileName + type);
        try (FileOutputStream fos = new FileOutputStream(outputPath.toFile())) {
            fos.write(imageBytes);
        }
    }

    public static String getFileToBase64(String folder, String name) {
        try {
            Path imagePath = Paths.get(folder, name);

            byte[] imageBytes = Files.readAllBytes(imagePath);

            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            log.error("[ERROR_COMMON] Error when get file: {}", folder + "/" + name);
            return null;
        }
    }

    public static void setParams(Query query, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            Set<Map.Entry<String, Object>> set = params.entrySet();
            for (Map.Entry<String, Object> obj : set) {
                if (obj.getValue() == null) query.setParameter(obj.getKey(), "");
                    //                else if ((obj.getKey().equals("propertyParam") && !addParam));
                else query.setParameter(obj.getKey(), obj.getValue());
            }
        }
    }

    public static String convertTimestampToDate(long millis) {
        LocalDateTime dateTime = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
                .toLocalDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return dateTime.format(formatter);
    }
}
