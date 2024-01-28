package com.nhnacademy.minidooray.task.backend.service.interfaces;

import com.nhnacademy.minidooray.task.backend.domain.dto.comment.CommentDto;
import com.nhnacademy.minidooray.task.backend.domain.dto.task.TaskInfoResponseDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentRequest;
import com.nhnacademy.minidooray.task.backend.domain.dto.task.TaskDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.task.TaskRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentModifyRequest;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<TaskInfoResponseDTO> findTaskListByProject(Long projectId);

    Optional<TaskDto> findTask(Long taskId);

    boolean registerTaskAndTaskTag(TaskRequest taskRequest);

    boolean deleteTask(Long taskId);

    boolean modifyTask(Long taskId, TaskRequest taskRequest);
}