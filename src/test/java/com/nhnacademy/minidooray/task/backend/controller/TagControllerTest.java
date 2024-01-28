package com.nhnacademy.minidooray.task.backend.controller;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.task.backend.domain.dto.tag.TagDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.tag.TagNameOnlyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.tag.TagRegisterRequest;
import com.nhnacademy.minidooray.task.backend.service.interfaces.TagService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TagController.class)
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    @Test
    @DisplayName("Project ID로 Tag list 조회")
    void testGetTagsWhenFindByProjectId() throws Exception {
        List<TagDTO> tagDTOList = List.of(new TagDTO() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "testTag";
            }
        });

        when(tagService.findAllByProjectId(1L))
                .thenReturn(tagDTOList);

        mockMvc.perform(get("/tag/list")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("projectId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo("testTag")));

        verify(tagService).findAllByProjectId(1L);
        verify(tagService, never()).findAllByTaskId(anyLong());
    }

    @Test
    @DisplayName("Task ID로 Tag list 조회")
    void testGetTagsWhenFindByTaskId() throws Exception {
        List<TagDTO> tagDTOList = List.of(new TagDTO() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "testTag";
            }
        });

        when(tagService.findAllByTaskId(1L))
                .thenReturn(tagDTOList);

        mockMvc.perform(get("/tag/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("taskId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo("testTag")));

        verify(tagService, never()).findAllByProjectId(1L);
        verify(tagService).findAllByTaskId(anyLong());
    }

    @Test
    @DisplayName("Project ID, Task Id 모두 있는경우, Tag list 조회 실패")
    void testGetTagsFailedWhenBothProjectIdAndTaskIdExist() throws Exception {
        mockMvc.perform(get("/tag/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("projectId", "1")
                        .param("taskId", "1"))
                .andExpect(status().isBadRequest());

        verify(tagService, never()).findAllByProjectId(anyLong());
        verify(tagService, never()).findAllByTaskId(anyLong());
    }

    @Test
    @DisplayName("ID가 없는 경우, Tag list 조회 실패")
    void testGetTagsFailedWhenIdIsNotProvided() throws Exception {
        mockMvc.perform(get("/tag/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(tagService, never()).findAllByProjectId(anyLong());
        verify(tagService, never()).findAllByTaskId(anyLong());
    }

    @Test
    @DisplayName("Tag 생성 성공")
    void testCreateTagSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TagRegisterRequest request = new TagRegisterRequest(1L, "test");

        when(tagService.createTag(request))
                .thenReturn(true);

        mockMvc.perform(post("/tag/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(tagService).createTag(request);
    }

    @Test
    @DisplayName("Tag 생성 실패")
    void testCreateTagFailed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TagRegisterRequest request = new TagRegisterRequest(1L, "test");

        when(tagService.createTag(request))
                .thenReturn(false);

        mockMvc.perform(post("/tag/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());

        verify(tagService).createTag(request);
    }

    @Test
    @DisplayName("Tag 수정 성공")
    void testModifyTagSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TagNameOnlyRequest request = new TagNameOnlyRequest("test");

        when(tagService.modifyTag(1L, request))
                .thenReturn(true);

        mockMvc.perform(put("/tag/{tagId}/modify", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(tagService).modifyTag(1L, request);
    }

    @Test
    @DisplayName("Tag 수정 실패")
    void testModifyTagFailed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TagNameOnlyRequest request = new TagNameOnlyRequest("test");

        when(tagService.modifyTag(1L, request))
                .thenReturn(false);

        mockMvc.perform(put("/tag/{tagId}/modify", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());

        verify(tagService).modifyTag(1L, request);
    }

    @Test
    @DisplayName("Tag 삭제 성공")
    void testDeleteTagSuccess() throws Exception {
        when(tagService.deleteTag(1L))
                .thenReturn(true);

        mockMvc.perform(delete("/tag/{tagId}/delete", 1L)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Tag 삭제 실패")
    void testDeleteTagFailed() throws Exception {
        when(tagService.deleteTag(1L))
                .thenReturn(false);

        mockMvc.perform(delete("/tag/{tagId}/delete", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }
}