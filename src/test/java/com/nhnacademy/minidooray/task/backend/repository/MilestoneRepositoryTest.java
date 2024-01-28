package com.nhnacademy.minidooray.task.backend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MilestoneDetailDto;
import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MilestoneDto;
import com.nhnacademy.minidooray.task.backend.entity.Milestone;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.Task;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class MilestoneRepositoryTest {

    @Autowired
    MilestoneRepository milestoneRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void findMilestoneByIdTest() {
        Milestone milestone = Milestone.builder()
                .name("jh")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .overOrNot("Y")
                .project(null)
                .build();

        Milestone saveMilestone = testEntityManager.persist(milestone);

        Optional<MilestoneDetailDto> optionalMilestone = milestoneRepository.findMilestoneById(saveMilestone.getId());
        assertTrue(optionalMilestone.isPresent());

        MilestoneDetailDto resultMilestone = optionalMilestone.get();

        assertEquals(saveMilestone.getId(), resultMilestone.getId());
        assertEquals(saveMilestone.getName(), resultMilestone.getName());
        assertEquals(saveMilestone.getStartDate(), resultMilestone.getStartDate());
        assertEquals(saveMilestone.getEndDate(), resultMilestone.getEndDate());
        assertEquals(saveMilestone.getOverOrNot(), resultMilestone.getOverOrNot());

    }


    @Test
    void testFindById() {
        Project saveProject = testEntityManager.persist(Project.builder()
                .name("dooray")
                .status("활성")
                .adminId("jkjk")
                .build());

        Milestone milestone = Milestone.builder()
                .name("jh")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .overOrNot("Y")
                .project(saveProject)
                .build();

        Milestone saveMilestone = testEntityManager.persistAndFlush(milestone);

        Optional<Milestone> optionalMilestone = milestoneRepository.findById(saveMilestone.getId());


        assertTrue(optionalMilestone.isPresent());
        Milestone resultMilestone = optionalMilestone.get();

        assertEquals(saveMilestone.getId(), resultMilestone.getId());
        assertEquals(saveMilestone.getName(), resultMilestone.getName());
        assertEquals(saveMilestone.getStartDate(), resultMilestone.getStartDate());
        assertEquals(saveMilestone.getEndDate(), resultMilestone.getEndDate());
        assertEquals(saveMilestone.getOverOrNot(), resultMilestone.getOverOrNot());
        assertEquals(saveMilestone.getProject(), resultMilestone.getProject());
    }

    @Test
    void findMileStoneByProjectIdTest() {
        Project saveProject = testEntityManager.persist(Project.builder()
                .name("dooray")
                .status("활성")
                .adminId("jkjk")
                .build());

        Milestone milestone = testEntityManager.persist(Milestone.builder()
                .name("jh")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .overOrNot("Y")
                .project(saveProject)
                .build());

        List<MilestoneDto> resultList = milestoneRepository.findMileStoneByProjectId(saveProject.getId());
        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        assertEquals(milestone.getId(), resultList.get(0).getId());
        assertEquals(milestone.getName(), resultList.get(0).getName());
    }

    @Test
    void findMileStoneByTaskId() {
        Project saveProject = testEntityManager.persist(Project.builder()
                .name("dooray")
                .status("활성")
                .adminId("jkjk")
                .build());

        Milestone milestone = testEntityManager.persist(Milestone.builder()
                .name("jh")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .overOrNot("Y")
                .project(saveProject)
                .build());

        Task task = testEntityManager.persist(Task.builder()
                .name("hi")
                .detail("hi")
                .project(saveProject)
                .milestone(milestone)
                .build());


        Optional<MilestoneDto> resultMilestoneDto = milestoneRepository.findMileStoneByTaskId(task.getId());
        assertTrue(resultMilestoneDto.isPresent());
        MilestoneDto resultMilestone = resultMilestoneDto.get();

        assertEquals(milestone.getId(), resultMilestone.getId());
        assertEquals(milestone.getName(), resultMilestone.getName());

    }

    @Test
    void deleteMilestoneByIdTest(){
        Milestone milestone = testEntityManager.persist(Milestone.builder()
                .name("jh")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .overOrNot("Y")
                .project(null)
                .build());

        milestoneRepository.deleteMilestoneById(milestone.getId());

        Optional<Milestone> resultMilestone = milestoneRepository.findById(milestone.getId());

        assertFalse(resultMilestone.isPresent());
    }

}