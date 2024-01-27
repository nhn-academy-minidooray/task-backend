package com.nhnacademy.minidooray.task.backend.service;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.minidooray.task.backend.domain.MilestoneRequest;
import com.nhnacademy.minidooray.task.backend.entity.Milestone;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectServiceTest {
    @Autowired
    ProjectService projectService;


    @Test
    void test(){
        MilestoneRequest milestone = new MilestoneRequest("1234", LocalDate.of(2022, 1, 1), LocalDate.of(2024, 6, 27));


        projectService.createMileStone(milestone, 1L);
    }


}