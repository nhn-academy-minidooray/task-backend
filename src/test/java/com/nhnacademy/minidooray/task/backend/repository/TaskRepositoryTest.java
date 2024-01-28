package com.nhnacademy.minidooray.task.backend.repository;

import javax.validation.Valid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @Test
    void test() {
//        taskRepository.taskListByProject(1L).stream().forEach(System.out::println);
//        taskRepository.existsById(1L);

//        System.out.println(taskRepository.findTaskByProjectIdAndId(1L,1L));
    }

    @Test
    void a(){
//        taskRepository.taskList(1L);
    }

}