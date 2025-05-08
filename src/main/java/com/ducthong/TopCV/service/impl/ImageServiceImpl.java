package com.ducthong.TopCV.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ducthong.TopCV.constant.Constants;
import com.ducthong.TopCV.exceptions.AppException;
import com.ducthong.TopCV.utility.Common;
import com.ducthong.TopCV.utility.StringUtils;
import org.springframework.stereotype.Service;

import com.ducthong.TopCV.domain.entity.Image;
import com.ducthong.TopCV.service.CloudinaryService;
import com.ducthong.TopCV.service.ImageService;
import com.ducthong.TopCV.utility.TimeUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final CloudinaryService cloudinaryService;
    private final String BANNER_FOLDER = "static";
    @Override
    public List<Image> uploadListBase64Image(List<String> base64StringList, String folder) throws IOException {
        List<Image> res = new ArrayList<>();
        for (String item : base64StringList) {
            Map resultUpload = cloudinaryService.uploadFileBase64(item, folder);
            Image imageUpload = Image.builder()
                    .name((String) resultUpload.get("original_filename"))
                    .imageUrl((String) resultUpload.get("url"))
                    .imagePublicId((String) resultUpload.get("public_id"))
                    .whenCreated(TimeUtil.getDateTimeNow())
                    .build();
            res.add(imageUpload);
        }
        return res;
    }

    @Override
    public String getOneImageById(String id) {
        String result = Common.getFileToBase64(BANNER_FOLDER, id + Constants.IMAGE_TYPE);
        if (StringUtils.isNullOrEmpty(result))
            throw new AppException("Không tìm thấy ảnh");
        return result;
    }
}
