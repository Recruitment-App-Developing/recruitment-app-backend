package com.ducthong.TopCV.extract_data.entity;

import com.ducthong.TopCV.constant.TimeFormatConstant;
import com.ducthong.TopCV.domain.entity.Auditable;
import com.ducthong.TopCV.domain.entity.CvProfile.Education;
import com.ducthong.TopCV.domain.entity.CvProfile.Experience;
import com.ducthong.TopCV.extract_data.dto.CvInforItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cv_infors")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "CvInforItem",
                classes = @ConstructorResult(
                    targetClass = CvInforItem.class,
                    columns = {
                            @ColumnResult(name = "cvInforId", type = String.class),
                            @ColumnResult(name = "fullName", type = String.class),
                            @ColumnResult(name = "cvName", type = String.class),
                            @ColumnResult(name = "applicationPosition", type = String.class),
                            @ColumnResult(name = "address", type = String.class),
                            @ColumnResult(name = "awardQuantity", type = Integer.class),
                            @ColumnResult(name = "educationQuantity", type = Integer.class),
                            @ColumnResult(name = "experienceQuantity", type = Integer.class)
                    }
                )
        )
})
public class CvInfor extends Auditable implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cv_infor_id")
    private String id;
    private String fullName;
    private String email;
    @DateTimeFormat(pattern = TimeFormatConstant.DATE_FORMAT)
    private LocalDateTime dateOfBirth;
    private String phone;
    private String address;
    private String applicationPostion;
    private String technicalSkills;
    private String softSkills;

    private String cvId;

    @OneToMany(mappedBy = "cvInfor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Award> awards = new ArrayList<>();
//    @OneToMany(mappedBy = "cvInfor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<Education> educations = new ArrayList<>();
//    @OneToMany(mappedBy = "cvInfor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<Experience> experiences = new ArrayList<>();
    @OneToMany(mappedBy = "cvInfor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Education> educations = new ArrayList<>();
    @OneToMany(mappedBy = "cvInfor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Experience> experiences = new ArrayList<>();

    @Column(name = "creator")
    private Integer creator;
}