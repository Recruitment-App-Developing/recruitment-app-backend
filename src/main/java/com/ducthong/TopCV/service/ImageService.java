package com.ducthong.TopCV.service;

import java.io.IOException;
import java.util.List;

import com.ducthong.TopCV.domain.entity.Image;

public interface ImageService {

    List<Image> uploadListBase64Image(List<String> base64StringList, String folder) throws IOException;
}
