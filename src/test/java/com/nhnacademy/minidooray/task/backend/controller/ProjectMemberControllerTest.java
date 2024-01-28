package com.nhnacademy.minidooray.task.backend.controller;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MemberIdOnlyDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.member.ProjectMemberRegisterRequest;
import com.nhnacademy.minidooray.task.backend.service.interfaces.ProjectMemberService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProjectMemberController.class)
class ProjectMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectMemberService projectMemberService;

    @Test
    void testGetProjectMembers() throws Exception {
        List<MemberIdOnlyDTO> memberIdOnlyDTOList = List.of(new MemberIdOnlyDTO() {

                                                                @Override
                                                                public String getAccountId() {
                                                                    return "tester";
                                                                }
                                                            }

        );

        when(projectMemberService.getProjectMembers(1L))
                .thenReturn(memberIdOnlyDTOList);

        mockMvc.perform(get("/member/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("projectId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountId", equalTo("tester")));

        verify(projectMemberService).getProjectMembers(1L);
    }

    @Test
    @DisplayName("멤버 등록 성공")
    void testRegisterProjectMemberSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ProjectMemberRegisterRequest request = new ProjectMemberRegisterRequest(1L, "tester");

        when(projectMemberService.registerProjectMember(request))
                .thenReturn(true);

        mockMvc.perform(post("/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("멤버 등록 실패")
    void testRegisterProjectMemberFailed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ProjectMemberRegisterRequest request = new ProjectMemberRegisterRequest(1L, "tester");

        when(projectMemberService.registerProjectMember(request))
                .thenReturn(false);

        mockMvc.perform(post("/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("멤버 삭제 성공")
    void testDeleteProjectMemberSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ProjectMemberRegisterRequest request = new ProjectMemberRegisterRequest(1L, "tester");

        when(projectMemberService.deleteProjectMember(request))
                .thenReturn(true);

        mockMvc.perform(delete("/member/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("멤버 삭제 실패")
    void testDeleteProjectMemberFailed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ProjectMemberRegisterRequest request = new ProjectMemberRegisterRequest(1L, "tester");

        when(projectMemberService.deleteProjectMember(request))
                .thenReturn(false);

        mockMvc.perform(delete("/member/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

}