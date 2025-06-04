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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "experiences")
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
@AuditTable(value = "experience_audit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "experience_id")
    private String id;

    private String companyName;
    private String position;

    @DateTimeFormat(pattern = TimeFormatConstant.MONTH_YEAR)
    private LocalDateTime startTime;

    public String getStartTime() {
        return TimeUtil.toMonthYear(this.startTime);
    }

    @DateTimeFormat(pattern = TimeFormatConstant.MONTH_YEAR)
    private LocalDateTime endTime;

    public String getEndTime() {
        return TimeUtil.toMonthYear(this.endTime);
    }

    private String timeStr;

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
