package com.ducthong.TopCV.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.ducthong.TopCV.constant.TimeFormatConstant;
import com.ducthong.TopCV.utility.TimeUtil;
import jakarta.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer id;

    private String name;

    private String imageUrl;

    private String imagePublicId;

    @DateTimeFormat(pattern = TimeFormatConstant.FULL_DATETIME_FORMAT)
    private LocalDateTime whenCreated;

    public Image(String name, String url){
        this.setName(name);
        this.setImageUrl(url);
        this.setWhenCreated(TimeUtil.getDateTimeNow());
    }
}
