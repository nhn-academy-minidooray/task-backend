package com.nhnacademy.minidooray.task.backend.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.task.backend.domain.dto.task.TaskDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.task.TaskRequest;
import com.nhnacademy.minidooray.task.backend.service.interfaces.TaskService;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskService taskService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void taskDtoListTest() throws Exception {
        Long projectId = 1L;
        when(taskService.findTaskListByProject(projectId)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/task/list?projectId={projectId}", projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void findTaskDtoTest() throws Exception {
        Long taskId = 1L;
        TaskDto taskDto = new TaskDto(1L, "jaehyeon", "is test");
        when(taskService.findTask(taskId)).thenReturn(Optional.of(taskDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/task/{taskId}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskDto.getId()))
                .andExpect(jsonPath("$.name").value(taskDto.getName()))
                .andExpect(jsonPath("$.detail").value(taskDto.getDetail()));
    }

    @Test
    void findTaskDtoFailTest() throws Exception {
        Long taskId = 1L;
        when(taskService.findTask(taskId)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/task/{taskId}", taskId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTaskTest() throws Exception {
        TaskRequest taskRequest = new TaskRequest(1L, "jaehyeon", 1L, Collections.emptyList(), "detail");
        when(taskService.registerTaskAndTaskTag(taskRequest)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/task/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void createTaskFailTest() throws Exception {
        TaskRequest taskRequest = new TaskRequest(1L, "jaehyeon", 1L, Collections.emptyList(), "detail");
        when(taskService.registerTaskAndTaskTag(taskRequest)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/task/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    void modifyTaskTest() throws Exception {
        Long taskId = 1L;
        TaskRequest taskRequest = new TaskRequest(1L, "jaehyeon", 1L, Collections.emptyList(), "detail");
        when(taskService.modifyTask(taskId, taskRequest)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/task/{taskId}/modify", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void modifyTaskFailTest() throws Exception {
        Long taskId = 1L;
        TaskRequest taskRequest = new TaskRequest(1L, "jaehyeon", 1L, Collections.emptyList(), "detail");
        when(taskService.modifyTask(taskId, taskRequest)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put("/task/{taskId}/modify", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteTaskTest() throws Exception {
        Long taskId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/task/{taskId}/delete", taskId))
                .andExpect(status().isNoContent());

        verify(taskService, times(1)).deleteTask(taskId);
    }


}