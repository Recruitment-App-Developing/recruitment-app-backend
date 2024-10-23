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
@Table(name = "educations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "education_id")
    private String id;
    private String schoolName;
    private String mainIndustry;

    @DateTimeFormat(pattern = TimeFormatConstant.DATE_FORMAT)
    private LocalDateTime startTime;

    public String getStartTime(){
        return TimeUtil.toMonthYear(this.startTime);
    }

    @DateTimeFormat(pattern = TimeFormatConstant.DATE_FORMAT)
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
