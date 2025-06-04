package com.ducthong.TopCV.extract_data.repository.impl;

import com.ducthong.TopCV.extract_data.dto.CvInforItem;
import com.ducthong.TopCV.extract_data.repository.CvInforRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CvInforRepositoryCustomImpl implements CvInforRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public List<CvInforItem> getListCvInfor() {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("select ci.cv_infor_id cvInforId, ci.full_name fullName, cv.name cvName, ci.application_postion applicationPosition, ci.address address, " +
                " count(distinct a.id) awardQuantity, count(distinct e.education_id) educationQuantity, count(distinct ex.experience_id) experienceQuantity ");
        sqlBuilder.append(" from cv_infors ci left join cvs cv on ci.cv_id = cv.cv_id " +
                " left join awards a on a.cv_infor_id = ci.cv_infor_id " +
                " left join educations e on e.cv_infor_id = ci.cv_infor_id " +
                " left join experiences ex on ex.cv_infor_id = ci.cv_infor_id ");
        sqlBuilder.append(" group by ci.cv_infor_id, ci.full_name, cv.name, ci.application_postion, ci.address, ci.created_at ");
        sqlBuilder.append(" order by ci.created_at desc ");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString(), "CvInforItem");

        List<CvInforItem> results = query.getResultList();
        return results;
    }
}
