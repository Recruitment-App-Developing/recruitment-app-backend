package com.ducthong.TopCV.extract_data.dto;

import lombok.Data;

@Data
public class AwardAuditResponse {
    private String id;
    private Integer rev;
    private Integer revtype;
    private String name;
    private String time;
    private String revtstmp;
}
