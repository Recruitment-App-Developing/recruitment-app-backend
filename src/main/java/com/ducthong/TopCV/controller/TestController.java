package com.ducthong.TopCV.controller;

import com.ducthong.TopCV.domain.dto.job.SearchJobRequestDTO;
import com.ducthong.TopCV.domain.entity.Job;
import com.ducthong.TopCV.domain.enums.ApplicationStatus;
import com.ducthong.TopCV.repository.JobRepository;
import com.ducthong.TopCV.repository.dynamic_query.CustomJobRepository;

import com.ducthong.TopCV.repository.objects.StatisticJobByIndustryObject;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;

@Tag(name = "Test Controller", description = "APIs related to Test operations")
@RestController
@RequiredArgsConstructor
public class TestController {
    private final JobRepository jobRepo;
    private final CustomJobRepository repo;
//    @PostMapping("/test")
//    public void test1(@RequestParam @Email(message = "Hello") String email) {
//        System.out.println(jobRepo.statisticGeneralJobByIndustry().getFirst().numberOfJob().toString());
//    }
    @GetMapping("/test")
    public void test2(
            @ParameterObject SearchJobRequestDTO requestDTO
            ) {
//        List<AbstractMap.SimpleEntry<LocalDate, Integer>> temp = repo.statisticGeneralJobByDay();
//        List<StatisticJobByIndustryObject> temp = repo.statisticCompanyJobByIndustry(1);
//        List<AbstractMap.SimpleEntry<LocalDate, Integer>> temp = repo.statisticApplyCandidateByDay(1);
        List<Job> jobs = repo.searchJob(requestDTO);
        System.out.println("ok1");
//        for (Job item : jobs){
//            System.out.println(item.getId() + " | "+ item.getName() + " | "+item.getCompany().getName());
//        }
    }
}
