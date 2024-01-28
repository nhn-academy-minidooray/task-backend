package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.dto.task.TaskInfoResponseDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.task.TaskRequest;
import com.nhnacademy.minidooray.task.backend.service.interfaces.TaskService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaskServiceImplTest {

    @Autowired
    TaskService taskService;

    @Test
    void test() {
        List<TaskInfoResponseDTO> taskListByProject = taskService.findTaskListByProject(1L);
        taskListByProject.stream().forEach(System.out::println);

    }

    @Test
    void a() {
        TaskRequest taskRequest = TaskRequest.builder().projectId(1L).name("hihi").milestoneId(1L).tagList(null).detail("qwer").build();

        System.out.println(taskService.createTask(taskRequest));
    }
}