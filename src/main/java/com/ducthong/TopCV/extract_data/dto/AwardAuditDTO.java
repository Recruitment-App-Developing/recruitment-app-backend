package com.ducthong.TopCV.extract_data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AwardAuditDTO {
    private String id;
    private Integer rev;
    private Integer revtype;
    private String name;
    private String time;
    private Long revtstmp;
}
