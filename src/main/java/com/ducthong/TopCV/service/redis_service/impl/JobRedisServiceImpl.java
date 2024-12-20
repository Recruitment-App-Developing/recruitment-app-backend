package com.ducthong.TopCV.service.redis_service.impl;

import com.ducthong.TopCV.domain.dto.job.JobResponseDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaRequestDTO;
import com.ducthong.TopCV.domain.dto.meta.MetaResponseDTO;
import com.ducthong.TopCV.responses.MetaResponse;
import com.ducthong.TopCV.service.redis_service.JobRedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JobRedisServiceImpl implements JobRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper redisObjectMapper;
    private static final String JOB_CACHE_PREFIX = "getListJob";
    private String getKey(MetaRequestDTO metaRequestDTO) {
        int currentPage = metaRequestDTO.currentPage();
        int pageSize = metaRequestDTO.pageSize();
        String key = String.format("getListJob:%d:%d", currentPage, pageSize);
        return key;
    }
    @Override
    public void clear() {
        Set<String> keys = redisTemplate.keys(JOB_CACHE_PREFIX + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    @Override
    public MetaResponse<MetaResponseDTO, List<JobResponseDTO>> getListJob(MetaRequestDTO metaRequestDTO) throws JsonProcessingException {
        String key = getKey(metaRequestDTO);
        String json = (String) redisTemplate.opsForValue().get(key);
        MetaResponse<MetaResponseDTO, List<JobResponseDTO>> res = json != null
                ? redisObjectMapper.readValue(json, new TypeReference<MetaResponse<MetaResponseDTO, List<JobResponseDTO>>>() {
                }) : null;
        return res;
    }

    @Override
    public void saveListJob(MetaRequestDTO metaRequestDTO, MetaResponse<MetaResponseDTO, List<JobResponseDTO>> saveObject) throws JsonProcessingException {
        String key = getKey(metaRequestDTO);
        String json = redisObjectMapper.writeValueAsString(saveObject);
        System.out.println(json);
        redisTemplate.opsForValue().set(key, json, 10, TimeUnit.SECONDS);
    }
}
