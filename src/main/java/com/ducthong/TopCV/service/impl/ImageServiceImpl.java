package com.ducthong.TopCV.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ducthong.TopCV.utility.TimeUtil;
import org.springframework.stereotype.Service;

import com.ducthong.TopCV.domain.entity.Image;
import com.ducthong.TopCV.service.CloudinaryService;
import com.ducthong.TopCV.service.ImageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final CloudinaryService cloudinaryService;
    // TODO
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
}
