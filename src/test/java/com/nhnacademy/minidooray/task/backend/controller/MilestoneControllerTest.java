package com.nhnacademy.minidooray.task.backend.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MilestoneDetailDto;
import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MilestoneDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.milestone.MilestoneRequest;
import com.nhnacademy.minidooray.task.backend.service.interfaces.ProjectService;
import java.time.LocalDate;
import java.util.List;
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
class MilestoneControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProjectService projectService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getMilestoneListTest() throws Exception {
        Long projectId = 1L;
        when(projectService.getMilestoneByProject(projectId)).thenReturn(List.of(
                new MilestoneDto(1L, "first"),
                new MilestoneDto(2L, "second")
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/milestone/list")
                        .param("projectId", String.valueOf(projectId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("first"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("second"));
    }

    @Test
    void getMilestoneTest() throws Exception {
        Long milestoneId = 1L;
        MilestoneDetailDto milestoneDetailDto = new MilestoneDetailDto() {
            @Override
            public Long getId() {
                return milestoneId;
            }

            @Override
            public String getName() {
                return "jaehyeon";
            }

            @Override
            public LocalDate getStartDate() {
                return LocalDate.now();
            }

            @Override
            public LocalDate getEndDate() {
                return LocalDate.now().plusDays(3);
            }

            @Override
            public String getOverOrNot() {
                return "Y";
            }
        };


        when(projectService.getMilestoneById(milestoneId)).thenReturn(
                Optional.of(milestoneDetailDto)
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/milestone/{milestoneId}", milestoneId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("jaehyeon"))
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.overOrNot").value("Y"));
    }

    @Test
    void getMilestoneFailTest() throws Exception {
        Long milestoneId = 1L;
        when(projectService.getMilestoneById(milestoneId)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/milestone/{milestoneId}", milestoneId)
        ).andExpect(status().isNotFound());
    }

    @Test
    void getMilestoneTaskIdTest() throws Exception {
        Long taskId = 1L;


        when(projectService.getMilestoneByTask(taskId)).thenReturn(
                Optional.of(new MilestoneDto(1L, "first"))
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/milestone")
                        .param("taskId", String.valueOf(taskId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("first"));
    }

    @Test
    void getMilestoneTaskIdFailTest() throws Exception {
        Long taskId = 1L;
        when(projectService.getMilestoneByTask(taskId)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/milestone").param("taskId", String.valueOf(taskId))
        ).andExpect(status().isNotFound());
    }

    @Test
    void createMilestoneTest() throws Exception {
        MilestoneRequest milestoneRequest =
                new MilestoneRequest(1L, "first", LocalDate.now(), LocalDate.now().plusDays(7));

        when(projectService.createMileStone(milestoneRequest)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/milestone/register")
                        .content(objectMapper.writeValueAsString(milestoneRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createMilestoneFailTest() throws Exception {
        Long projectId = 3L;
        MilestoneRequest milestoneRequest =
                new MilestoneRequest(projectId, "first", LocalDate.now(), LocalDate.now().plusDays(7));
        when(projectService.createMileStone(milestoneRequest)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("/milestone/register")
                        .content(objectMapper.writeValueAsString(milestoneRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateMilestoneTest() throws Exception {
        Long milestoneId = 1L;
        MilestoneRequest milestoneRequest =
                new MilestoneRequest(1L, "firstModify", LocalDate.now(), LocalDate.now().plusDays(14));

        when(projectService.updateMilestone(milestoneRequest, milestoneId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/milestone/{milestoneId}/modify", milestoneId)
                        .content(objectMapper.writeValueAsString(milestoneRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateMilestoneFailTest() throws Exception {
        Long milestoneId = 1L;
        MilestoneRequest milestoneRequest =
                new MilestoneRequest(1L, "firstModify", LocalDate.now(), LocalDate.now().plusDays(14));

        when(projectService.updateMilestone(milestoneRequest, milestoneId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put("/milestone/{milestoneId}/modify", milestoneId)
                        .content(objectMapper.writeValueAsString(milestoneRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteMilestoneTest() throws Exception {
        Long milestoneId = 1L;
        when(projectService.deleteMilestone(milestoneId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/milestone/{milestoneId}/delete", milestoneId))
                .andExpect(status().isOk());
    }

    @Test
    void deleteMilestoneFailTest() throws Exception {
        Long milestoneId = 1L;
        when(projectService.deleteMilestone(milestoneId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/milestone/{milestoneId}/delete", milestoneId))
                .andExpect(status().isConflict());
    }
}