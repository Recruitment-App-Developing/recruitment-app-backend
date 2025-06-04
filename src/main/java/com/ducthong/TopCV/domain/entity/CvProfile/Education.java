package com.ducthong.TopCV.domain.entity.CvProfile;

import java.time.LocalDateTime;

import com.ducthong.TopCV.domain.entity.Auditable;
import com.ducthong.TopCV.extract_data.entity.CvInfor;
import jakarta.persistence.*;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.format.annotation.DateTimeFormat;

import com.ducthong.TopCV.constant.TimeFormatConstant;
import com.ducthong.TopCV.utility.TimeUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "educations")
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
@AuditTable(value = "education_audit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "education_id")
    @JsonProperty("educationId")
    private String id;

    private String schoolName;
    private String mainIndustry;

    @DateTimeFormat(pattern = TimeFormatConstant.DATE_FORMAT)
    private LocalDateTime startTime;

    public String getStartTime() {
        return TimeUtil.toMonthYear(this.startTime);
    }

    @DateTimeFormat(pattern = TimeFormatConstant.DATE_FORMAT)
    private LocalDateTime endTime;

    private String timeStr;

    public String getEndTime() {
        return TimeUtil.toMonthYear(this.endTime);
    }

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String detail;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_profile_id")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private CvProfile cvProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_infor_id")
    private CvInfor cvInfor;
}
