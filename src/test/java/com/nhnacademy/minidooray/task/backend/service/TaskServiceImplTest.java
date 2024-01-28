package com.nhnacademy.minidooray.task.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.minidooray.task.backend.domain.dto.task.TaskDto;
import com.nhnacademy.minidooray.task.backend.domain.dto.task.TaskInfoResponseDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.task.TaskRequest;
import com.nhnacademy.minidooray.task.backend.entity.Milestone;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.Tag;
import com.nhnacademy.minidooray.task.backend.entity.Task;
import com.nhnacademy.minidooray.task.backend.entity.TaskTag;
import com.nhnacademy.minidooray.task.backend.repository.MilestoneRepository;
import com.nhnacademy.minidooray.task.backend.repository.ProjectRepository;
import com.nhnacademy.minidooray.task.backend.repository.TagRepository;
import com.nhnacademy.minidooray.task.backend.repository.TaskRepository;
import com.nhnacademy.minidooray.task.backend.repository.TaskTagRepository;
import com.nhnacademy.minidooray.task.backend.service.interfaces.TaskService;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class TaskServiceImplTest {

    @Autowired
    TaskService taskService;

    @MockBean
    TaskRepository taskRepository;

    @MockBean
    MilestoneRepository milestoneRepository;

    @MockBean
    ProjectRepository projectRepository;

    @MockBean
    TaskTagRepository taskTagRepository;

    @MockBean
    TagRepository tagRepository;


    @Test
    void findTaskNotFoundTest() {
        Long taskId = 1L;
        when(taskRepository.findTaskById(taskId)).thenReturn(Optional.empty());

        Optional<TaskDto> task = taskService.findTask(taskId);
        assertTrue(task.isEmpty());
        verify(taskRepository, times(1)).findTaskById(taskId);
    }

    @Test
    void deleteTaskTest() {
        Long taskId = 1L;

        when(taskRepository.existsById(taskId)).thenReturn(true);

        boolean result = taskService.deleteTask(taskId);

        assertTrue(result);
        verify(taskRepository, times(1)).deleteTaskById(taskId);
        verify(taskRepository, times(1)).existsById(taskId);
    }

    @Test
    void deleteTaskNotExistsTest() {
        Long taskId = 1L;

        when(taskRepository.existsById(taskId)).thenReturn(false);

        boolean result = taskService.deleteTask(taskId);

        assertFalse(result);
        verify(taskRepository, never()).deleteTaskById(taskId);
        verify(taskRepository, times(1)).existsById(taskId);
    }

    @Test
    void modifyTaskTest() {

        Long taskId = 1L;
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("Updated Task");
        taskRequest.setMilestoneId(2L);

        Optional<Milestone> milestone = Optional.of(new Milestone());
        when(milestoneRepository.findById(taskRequest.getMilestoneId())).thenReturn(milestone);

        Task existingTask = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));


        boolean result = taskService.modifyTask(taskId, taskRequest);


        assertFalse(result);
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(existingTask);
        verify(milestoneRepository, times(1)).findById(taskRequest.getMilestoneId());

        assertEquals(taskRequest.getName(), existingTask.getName());
        assertEquals(taskRequest.getDetail(), existingTask.getDetail());
    }

    @Test
    void registerTaskAndTaskTagTest() {
        Long projectId = 1L;
        Long milestoneId = 2L;
        String taskName = "Task1";
        String taskDetail = "Detail1";
        Long tagId = 3L;

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setProjectId(projectId);
        taskRequest.setMilestoneId(milestoneId);
        taskRequest.setName(taskName);
        taskRequest.setDetail(taskDetail);
        taskRequest.setTagList(Collections.singletonList(tagId));

        Project project = Project.builder().id(projectId).build();
        Milestone milestone = Milestone.builder().id(milestoneId).build();
        Task task = Task.builder()
                .name(taskName)
                .project(project)
                .milestone(milestone)
                .detail(taskDetail)
                .build();
        Task savedTask = Task.builder().id(1L).build();

        when(projectRepository.getProjectById(projectId)).thenReturn(Optional.of(project));
        when(milestoneRepository.findById(milestoneId)).thenReturn(Optional.of(milestone));
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(Tag.builder().id(tagId).build()));

        boolean result = taskService.registerTaskAndTaskTag(taskRequest);

        verify(projectRepository, times(1)).getProjectById(projectId);
        verify(milestoneRepository, times(1)).findById(milestoneId);
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(tagRepository, times(1)).findById(tagId);
        verify(taskTagRepository, times(1)).save(any(TaskTag.class));

        assertFalse(result);
    }


}