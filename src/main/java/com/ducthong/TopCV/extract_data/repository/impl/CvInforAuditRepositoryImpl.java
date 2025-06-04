package com.ducthong.TopCV.extract_data.repository.impl;

import com.ducthong.TopCV.extract_data.dto.AwardAuditDTO;
import com.ducthong.TopCV.extract_data.repository.CvInforAuditRepository;
import com.ducthong.TopCV.utility.Common;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CvInforAuditRepositoryImpl implements CvInforAuditRepository {
    private final EntityManager entityManager;

    @Override
    public List<AwardAuditDTO> getListAwardAuditById(String id) {
        List<AwardAuditDTO> results = new ArrayList<>();

        StringBuilder sqlBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        sqlBuilder.append("select aa.id id, aa.rev rev, aa.revtype revtype, aa.name name, aa.time time, re.revtstmp revtstmp ");
        sqlBuilder.append(" from award_audit aa ");
        sqlBuilder.append(" join revinfo re on aa.rev = re.rev ");
        sqlBuilder.append(" where aa.id = :id ");
        sqlBuilder.append(" order by re.revtstmp desc ");
        params.put("id", id);

        Query query = entityManager.createNativeQuery(sqlBuilder.toString(), "AwardAuditMapping");
        Common.setParams(query, params);

        results = query.getResultList();
        return results;
    }

    @Override
    public List<AwardAuditDTO> getListAwardAuditByCvInfoId(String cvInfoId) {
        List<String> ids = getAllIdByCvInforId(cvInfoId, "award");

        List<AwardAuditDTO> results = new ArrayList<>();

        StringBuilder sqlBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        sqlBuilder.append("select aa.id id, aa.rev rev, aa.revtype revtype, aa.name name, aa.time time, re.revtstmp revtstmp ");
        sqlBuilder.append(" from award_audit aa ");
        sqlBuilder.append(" join revinfo re on aa.rev = re.rev ");
        sqlBuilder.append(" where aa.id in :ids  ");
        sqlBuilder.append(" order by re.revtstmp desc ");
        params.put("ids", ids);

        Query query = entityManager.createNativeQuery(sqlBuilder.toString(), "AwardAuditMapping");
        Common.setParams(query, params);

        results = query.getResultList();
        return results;
    }

    private List<String> getAllIdByCvInforId(String cvInfoId, String type) {
        List<String> results = new ArrayList<>();

        StringBuilder sqlBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        sqlBuilder.append("select distinct aa.id ");
        sqlBuilder.append(" from award_audit aa ");
        sqlBuilder.append(" where aa.cv_infor_id = :cvInfoId");
        params.put("cvInfoId", cvInfoId);

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        Common.setParams(query, params);
        results = query.getResultList();

        return results;
    }

}
