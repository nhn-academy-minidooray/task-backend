package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.CommentDto;
import com.nhnacademy.minidooray.task.backend.domain.CommentRequest;
import com.nhnacademy.minidooray.task.backend.domain.TaskDto;
import com.nhnacademy.minidooray.task.backend.domain.TaskRequest;
import java.util.List;

public interface TaskService {
    List<TaskDto> findTaskListByProject(Long projectId);

    TaskDto findTask(Long projectId, Long taskId);

    void createTask(TaskRequest taskRequest, Long projectId);

    void deleteTask(Long taskId);

    List<CommentDto> findCommentListByTask(Long taskId);

    void createComment(CommentRequest commentRequest, Long taskId);

    void deleteComment(Long taskId);
}