package com.ducthong.TopCV.domain.entity.CvProfile;

import java.time.LocalDateTime;

import com.ducthong.TopCV.extract_data.entity.CvInfor;
import jakarta.persistence.*;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {
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
    private CvProfile cvProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_infor_id")
    private CvInfor cvInfor;
}
