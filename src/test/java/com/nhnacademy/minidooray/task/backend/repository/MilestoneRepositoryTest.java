package com.nhnacademy.minidooray.task.backend.repository;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MilestoneRepositoryTest {
    @Autowired
    MilestoneRepository milestoneRepository;

    @Test
    void test() {
//        List<List<Object>> mileStoneByProjectId = milestoneRepository.findMileStoneByProjectId(1L);

//        mileStoneByProjectId.stream().forEach(m -> System.out.println(m.get(1)));

    }

    @Test
    void test2() {
//        System.out.println(milestoneRepository.findMilestoneByProjectIdAndMilestoneId(1L, 1L).getName());
    }

}