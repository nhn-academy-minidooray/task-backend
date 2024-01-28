package com.nhnacademy.minidooray.task.backend.controller;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.task.backend.domain.dto.comment.CommentDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentModifyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentRequest;
import com.nhnacademy.minidooray.task.backend.service.interfaces.CommentService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    void testGetCommentList() throws Exception {
        List<CommentDto> commentDtoList = List.of(new CommentDto() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getOwner() {
                return "tester";
            }

            @Override
            public String getContent() {
                return "test";
            }
        });

        when(commentService.findCommentListByTask(1L))
                .thenReturn(commentDtoList);

        mockMvc.perform(get("/comment/list")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("taskId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].owner", equalTo("tester")))
                .andExpect(jsonPath("$[0].content", equalTo("test")));

        verify(commentService).findCommentListByTask(1L);
    }

    @Test
    @DisplayName("Comment 생성 성공")
    void testCreateCommentSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentRequest request = new CommentRequest(1L, "tester", "test");

        when(commentService.createComment(request)).thenReturn(true);

        mockMvc.perform(post("/comment/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(commentService).createComment(request);
    }

    @Test
    @DisplayName("Comment 생성 성공")
    void testCreateCommentFail() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentRequest request = new CommentRequest(1L, "tester", "test");

        when(commentService.createComment(request)).thenReturn(false);

        mockMvc.perform(post("/comment/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());

        verify(commentService).createComment(request);
    }

    @Test
    @DisplayName("Comment 생성 실패")
    void testCreateCommentFailed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentRequest request = new CommentRequest(1L, "tester", "test");

        when(commentService.createComment(request))
                .thenReturn(false);

        mockMvc.perform(post("/comment/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());

        verify(commentService).createComment(request);
    }

    @Test
    @DisplayName("Comment 수정 성공")
    void testModifyCommentSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentModifyRequest request = new CommentModifyRequest("tester", "test");

        when(commentService.modifyComment(1L, request))
                .thenReturn(true);

        mockMvc.perform(put("/comment/{commentId}/modify", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(commentService).modifyComment(1L, request);
    }

    @Test
    @DisplayName("Comment 수정 실패")
    void testModifyCommentFailed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentModifyRequest request = new CommentModifyRequest("tester", "test");

        when(commentService.modifyComment(1L, request))
                .thenReturn(false);

        mockMvc.perform(put("/comment/{commentId}/modify", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());

        verify(commentService).modifyComment(1L, request);
    }

    @Test
    @DisplayName("Comment 삭제 성공")
    void testDeleteCommentSuccess() throws Exception {
        when(commentService.deleteComment(1L))
                .thenReturn(true);

        mockMvc.perform(delete("/comment/{commentId}/delete", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(commentService).deleteComment(1L);
    }

    @Test
    @DisplayName("Comment 삭제 실패")
    void testDeleteCommentFailed() throws Exception {
        when(commentService.deleteComment(1L))
                .thenReturn(false);

        mockMvc.perform(delete("/comment/{commentId}/delete", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

        verify(commentService).deleteComment(1L);
    }
}