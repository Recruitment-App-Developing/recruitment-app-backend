package com.ducthong.TopCV.domain.entity.address;

import com.ducthong.TopCV.domain.entity.Job;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "JobAddress")
@Table(name = "job_address")
@Getter
@Setter
@NoArgsConstructor
public class JobAddress extends Address{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;
    @Builder
    public JobAddress(String detail, String provinceName, String provinceCode, String districtName, String districtCode, String wardName, String wardCode) {
        super(detail, provinceName, provinceCode, districtName, districtCode, wardName, wardCode);
    }
    public String toString(){
        return super.getProvinceName()+ ": " +
                super.getDetail()+", " +
                super.getWardName()+", " +
                super.getDistrictName();
    }
}
