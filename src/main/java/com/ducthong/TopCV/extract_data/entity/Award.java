package com.ducthong.TopCV.extract_data.entity;

import com.ducthong.TopCV.utility.StringUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "awards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Award {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String time;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_infor_id")
    private CvInfor cvInfor;

    public static boolean checkAllNull(Award award) {
        return StringUtils.isNullOrEmpty(award.getName()) && StringUtils.isNullOrEmpty(award.getTime());
    }
}
