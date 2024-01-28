package com.nhnacademy.minidooray.task.backend.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.task.backend.domain.dto.project.ProjectDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.project.ProjectRequest;
import com.nhnacademy.minidooray.task.backend.service.interfaces.ProjectService;
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
class ProjectControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProjectService projectService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getProjectListByAccountIdTest() throws Exception {
        String accountId = "testUser";
        ProjectDto projectDto = new ProjectDto() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "jaehyeon";
            }

            @Override
            public String getDetail() {
                return "is best";
            }

            @Override
            public String getAdminId() {
                return accountId;
            }

            @Override
            public String getStatus() {
                return "활성";
            }
        };
        when(projectService.getProjectListByAccountId(accountId))
                .thenReturn(Collections.singletonList(projectDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/project/list")
                        .param("accountId", accountId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("jaehyeon"))
                .andExpect(jsonPath("$[0].detail").value("is best"))
                .andExpect(jsonPath("$[0].status").value("활성"))
                .andExpect(jsonPath("$[0].adminId").value(accountId));
    }

    @Test
    void getProjectTest() throws Exception {
        Long projectId = 1L;
        ProjectDto projectDto = new ProjectDto() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "jaehyeon";
            }

            @Override
            public String getDetail() {
                return "is best";
            }

            @Override
            public String getAdminId() {
                return "account";
            }

            @Override
            public String getStatus() {
                return "활성";
            }
        };

        when(projectService.getProjectDtoById(projectId))
                .thenReturn(Optional.of(projectDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/project/{projectId}", projectId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("jaehyeon"))
                .andExpect(jsonPath("$.detail").value("is best"))
                .andExpect(jsonPath("$.status").value("활성"))
                .andExpect(jsonPath("$.adminId").value("account"));
    }

    @Test
    void getProjectFailTest() throws Exception {
        Long projectId = 1L;
        when(projectService.getProjectDtoById(projectId))
                .thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/project/{projectId}", projectId))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void createProject() throws Exception {
        ProjectRequest projectRequest = new ProjectRequest("project!", "nono", "admin");

        when(projectService.createProject(projectRequest)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/project/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void createProjectFail() throws Exception {
        ProjectRequest projectRequest = new ProjectRequest("project!", "nono", "admin");

        when(projectService.createProject(projectRequest)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/project/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequest)))
                .andExpect(status().isConflict());
    }


}