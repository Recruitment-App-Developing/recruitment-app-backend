package com.ducthong.TopCV.service;

import com.ducthong.TopCV.domain.entity.Image;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<Image> uploadListBase64Image(List<String> base64StringList, String folder) throws IOException;
}
