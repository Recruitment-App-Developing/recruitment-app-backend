package com.ducthong.TopCV.extract_data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "award_audit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwardAudit {

    @Id
    @Column(name = "id")
    private String id; // id của Award

    @Column(name = "time")
    private String time;

    @Column(name = "name")
    private String name;

    @Column(name = "cv_infor_id")
    private String cvInforId;

    @Column(name = "rev")
    private Integer rev;

    @Column(name = "revtype")
    private Integer revType;
}
