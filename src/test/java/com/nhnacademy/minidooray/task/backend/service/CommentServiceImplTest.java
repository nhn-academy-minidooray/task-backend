package com.nhnacademy.minidooray.task.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.minidooray.task.backend.domain.dto.comment.CommentDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentModifyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentRequest;
import com.nhnacademy.minidooray.task.backend.entity.Comment;
import com.nhnacademy.minidooray.task.backend.entity.Task;
import com.nhnacademy.minidooray.task.backend.repository.CommentRepository;
import com.nhnacademy.minidooray.task.backend.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CommentDto commentDto;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void testFindCommentListByTask() {
        List<CommentDto> commentDtoList = List.of(commentDto);

        when(commentRepository.findAllByTask_Id(1L))
                .thenReturn(commentDtoList);

        List<CommentDto> result = commentService.findCommentListByTask(1L);

        assertEquals(commentDtoList, result);

        verify(commentRepository).findAllByTask_Id(1L);
    }

    @Test
    @DisplayName("Comment 생성 성공")
    void testCreateCommentSuccess() {
        CommentRequest request = new CommentRequest(1L, "tester", "test");
        Task task =new Task();

        when(taskRepository.getTaskById(request.getTaskId()))
                .thenReturn(Optional.of(task));

        boolean result = commentService.createComment(request);

        assertTrue(result);

        verify(taskRepository).getTaskById(request.getTaskId());
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    @DisplayName("Comment 생성 실패")
    void testCreateCommentFailed() {
        CommentRequest request = new CommentRequest(1L, "tester", "test");

        when(taskRepository.getTaskById(request.getTaskId()))
                .thenReturn(Optional.empty());

        boolean result = commentService.createComment(request);

        assertFalse(result);

        verify(taskRepository).getTaskById(request.getTaskId());
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    @DisplayName("Comment 수정 성공")
    void testModifyCommentSuccess() {
        CommentModifyRequest request = new CommentModifyRequest("tester", "test");
        Comment comment = Comment.builder()
                .owner("tester")
                .content("test")
                .build();

        when(commentRepository.findById(1L))
                .thenReturn(Optional.of(comment));

        boolean result = commentService.modifyComment(1L, request);

        assertTrue(result);
        assertEquals(request.getOwner(), comment.getOwner());
        assertEquals(request.getContent(), comment.getContent());

        verify(commentRepository).findById(1L);
        verify(commentRepository).save(comment);
    }

    @Test
    @DisplayName("Comment 수정 실패")
    void testModifyCommentFailed() {
        CommentModifyRequest request = new CommentModifyRequest("tester", "test");

        when(commentRepository.findById(1L))
                .thenReturn(Optional.empty());

        boolean result = commentService.modifyComment(1L, request);

        assertFalse(result);

        verify(commentRepository).findById(1L);
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    @DisplayName("Comment 삭제 성공")
    void testDeleteCommentSuccess() {
        when(commentRepository.existsById(1L))
                .thenReturn(true);

        boolean result = commentService.deleteComment(1L);

        assertTrue(result);

        verify(commentRepository).existsById(1L);
        verify(commentRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Comment 삭제 실패")
    void testDeleteCommentFailed() {
        when(commentRepository.existsById(1L))
                .thenReturn(false);

        boolean result = commentService.deleteComment(1L);

        assertFalse(result);

        verify(commentRepository).existsById(1L);
        verify(commentRepository, never()).deleteById(anyLong());
    }

}