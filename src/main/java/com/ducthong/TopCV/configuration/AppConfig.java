package com.ducthong.TopCV.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppConfig {
    @Value("${cloudinary.folder.avatar}")
    private String FOLDER_AVATAR;
    @Value("${cloudinary.folder.default-avatar}")
    private String DEFAULT_AVATAR;
    @Value("${cloudinary.folder.company-logo}")
    private String FOLDER_COMPANY_LOGO;
    @Value("${cloudinary.folder.default-company-logo}")
    private String DEFAULT_COMPANY_LOGO;
}
