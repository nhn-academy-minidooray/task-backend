package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.CommentDto;
import com.nhnacademy.minidooray.task.backend.domain.CommentRequest;
import com.nhnacademy.minidooray.task.backend.domain.TaskDto;
import com.nhnacademy.minidooray.task.backend.domain.TaskRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.CommentModifyRequest;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<TaskDto> findTaskListByProject(Long projectId);

    Optional<TaskDto> findTask(Long taskId);

    boolean createTask(TaskRequest taskRequest);

    boolean deleteTask(Long taskId);

    List<CommentDto> findCommentListByTask(Long taskId);

    boolean createComment(CommentRequest commentRequest, Long taskId);

    boolean modifyComment(Long commentId, CommentModifyRequest commentModifyRequest);

    boolean modifyTask(Long taskId, TaskRequest taskRequest);

    boolean deleteComment(Long taskId);
}