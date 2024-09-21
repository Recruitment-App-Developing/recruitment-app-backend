package com.ducthong.TopCV.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ducthong.TopCV.domain.dto.cloudinary.CloudinaryResponseDTO;
import com.ducthong.TopCV.service.CloudinaryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    @Value("${cloudinary.folder.default}")
    private String defaultFolder;

    @Override
    public Map uploadFileBase64(String base64String, String folder) throws IOException {
        if (folder == null) folder = defaultFolder;
        Map result = cloudinary.uploader().upload(base64String, ObjectUtils.asMap("folder", folder));
        return result;
    }

    @Override
    public CloudinaryResponseDTO uploadFileBase64_v2(String base64String, String folder) throws IOException {
        if (folder == null) folder = defaultFolder;
        Map result = cloudinary.uploader().upload(base64String, ObjectUtils.asMap("folder", folder));
        return CloudinaryResponseDTO.builder()
                .name((String) result.get("original_filename"))
                .url((String) result.get("url"))
                .public_id((String) result.get("public_id"))
                .build();
    }

    public Map upload(MultipartFile multipartFile, String folder) throws IOException {
        File file = convert(multipartFile);
        if (folder == null) folder = defaultFolder;
        Map result = cloudinary.uploader().upload(file, ObjectUtils.asMap("folder", folder));
        if (!Files.deleteIfExists(file.toPath())) {
            throw new RuntimeException("Failed to delete temporary file: " + file.getAbsolutePath());
        }
        return result;
    }

    public Map delete(String id) throws IOException {
        return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }
}
