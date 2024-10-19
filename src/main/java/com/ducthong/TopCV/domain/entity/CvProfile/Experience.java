package com.ducthong.TopCV.domain.entity.CvProfile;

import com.ducthong.TopCV.constant.TimeFormatConstant;
import com.ducthong.TopCV.domain.entity.account.Candidate;
import com.ducthong.TopCV.utility.TimeUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "experiences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "experience_id")
    private String id;
    private String companyName;
    private String position;

    @DateTimeFormat(pattern = TimeFormatConstant.MONTH_YEAR)
    private LocalDateTime startTime;

    public String getStartTime(){
        return TimeUtil.toMonthYear(this.startTime);
    }

    @DateTimeFormat(pattern = TimeFormatConstant.MONTH_YEAR)
    private LocalDateTime endTime;

    public String getEndTime(){
        return TimeUtil.toMonthYear(this.endTime);
    }

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String detail;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_profile_id")
    private CvProfile cvProfile;
}
