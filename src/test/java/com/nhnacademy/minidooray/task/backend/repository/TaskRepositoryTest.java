package com.nhnacademy.minidooray.task.backend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nhnacademy.minidooray.task.backend.domain.dto.task.TaskDto;
import com.nhnacademy.minidooray.task.backend.entity.Milestone;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.Tag;
import com.nhnacademy.minidooray.task.backend.entity.Task;
import com.nhnacademy.minidooray.task.backend.entity.TaskTag;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TestEntityManager testEntityManager;


    @Test
    public void testFindTaskById() {

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

        Task task = testEntityManager.persist(
                Task.builder().name("hihi").detail("test").project(saveProject).milestone(milestone).build());


        Optional<TaskDto> result = taskRepository.findTaskById(task.getId());

        assertTrue(result.isPresent());
        assertEquals(task.getId(), result.get().getId());
        assertEquals(task.getName(), result.get().getName());
        assertEquals(task.getDetail(), result.get().getDetail());
    }

    @Test
    void testDeleteTaskById() {
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
                .name("hihi")
                .detail("test")
                .project(saveProject)
                .milestone(milestone)
                .build());


        taskRepository.deleteTaskById(task.getId());
        Optional<Task> result = taskRepository.findById(task.getId());
        assertFalse(result.isPresent());
    }

    @Test
    void getTaskByIdTest() {
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
                .name("hihi")
                .detail("test")
                .project(saveProject)
                .milestone(milestone)
                .build());

        Optional<Task> resultOptional = taskRepository.getTaskById(task.getId());
        assertTrue(resultOptional.isPresent());
        Task result = resultOptional.get();

        assertEquals(task.getId(), result.getId());
        assertEquals(task.getName(), result.getName());
        assertEquals(task.getDetail(), result.getDetail());
        assertEquals(task.getProject(), result.getProject());
        assertEquals(task.getMilestone(), result.getMilestone());
    }

    @Test
    void nativeTaskListTest() {
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
                .name("hihi")
                .detail("test")
                .project(saveProject)
                .milestone(milestone)
                .build());

        Tag tag = testEntityManager.persist(Tag.builder()
                .name("tag")
                .project(saveProject)
                .build());

        TaskTag.Pk pk = TaskTag.Pk.builder().taskId(task.getId()).tagId(tag.getId()).build();
        TaskTag taskTag = testEntityManager.persist(TaskTag.builder()
                .pk(pk)
                .tag(tag)
                .task(task)
                .build());

//        List<List<Object>> nativeTaskList = taskRepository.nativeTaskList(saveProject.getId());
//
//        Assertions.assertEquals(1, nativeTaskList.size());
//        Assertions.assertEquals(6, nativeTaskList.size());


    }


}