package com.nhnacademy.minidooray.task.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentModifyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    @Order(7)
//    void testGetCommentList() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        TaskIdOnlyRequest taskIdOnlyRequest = new TaskIdOnlyRequest(1L);
//
//        mockMvc.perform(get("/comment/list")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(taskIdOnlyRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id", equalTo(1)))
//                .andExpect(jsonPath("$[0].owner", equalTo("tester")))
//                .andExpect(jsonPath("$[0].content", equalTo("test")));
//    }

    @Test
    @Order(1)
    @DisplayName("Comment 생성 성공")
    void testCreateCommentSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentRequest request = new CommentRequest(100L, "tester", "test");

        mockMvc.perform(post("/comment/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    @DisplayName("Comment 생성 실패")
    void testCreateCommentFailed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentRequest request = new CommentRequest(100L, "tester", "test");

        mockMvc.perform(post("/comment/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(3)
    @DisplayName("Comment 수정 성공")
    void testModifyCommentSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentModifyRequest request = new CommentModifyRequest("tester", "modified");

        mockMvc.perform(put("/comment/{commentId}/modify", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    @DisplayName("Comment 수정 실패")
    void testModifyCommentFailed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentModifyRequest request = new CommentModifyRequest("tester", "modified");

        mockMvc.perform(put("/comment/{commentId}/modify", 101L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(5)
    @DisplayName("Comment 삭제 성공")
    void testDeleteCommentSuccess() throws Exception {
        mockMvc.perform(delete("/comment/{commentId}/delete", 100L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    @DisplayName("Comment 삭제 실패")
    void testDeleteCommentFailed() throws Exception {
        mockMvc.perform(delete("/comment/{commentId}/delete", 100L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

}