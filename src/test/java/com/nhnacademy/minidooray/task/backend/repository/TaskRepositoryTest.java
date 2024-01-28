package com.nhnacademy.minidooray.task.backend.repository;

import com.nhnacademy.minidooray.task.backend.domain.dto.task.TaskInfoResponseDTO;
import java.util.List;
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
        List<List<Object>> taskInfoResponseDTOS = taskRepository.nativeTaskList(1L);


        taskInfoResponseDTOS.stream().forEach(System.out::println);
    }

}