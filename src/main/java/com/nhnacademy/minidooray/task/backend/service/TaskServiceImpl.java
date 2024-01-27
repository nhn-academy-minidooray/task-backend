package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.CommentDto;
import com.nhnacademy.minidooray.task.backend.domain.CommentRequest;
import com.nhnacademy.minidooray.task.backend.domain.TaskDto;
import com.nhnacademy.minidooray.task.backend.domain.TaskRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.CommentModifyRequest;
import com.nhnacademy.minidooray.task.backend.entity.Comment;
import com.nhnacademy.minidooray.task.backend.entity.Milestone;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.Task;
import com.nhnacademy.minidooray.task.backend.repository.CommentRepository;
import com.nhnacademy.minidooray.task.backend.repository.MilestoneRepository;
import com.nhnacademy.minidooray.task.backend.repository.ProjectRepository;
import com.nhnacademy.minidooray.task.backend.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final MilestoneRepository milestoneRepository;

    private final ProjectRepository projectRepository;

    private final CommentRepository commentRepository;

    public TaskServiceImpl(TaskRepository taskRepository, MilestoneRepository milestoneRepository,
                           ProjectRepository projectRepository, CommentRepository commentRepository) {
        this.taskRepository = taskRepository;
        this.milestoneRepository = milestoneRepository;
        this.projectRepository = projectRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<TaskDto> findTaskListByProject(Long projectId) {
        return taskRepository.taskListByProjectId(projectId);
    }

    @Override
    public Optional<TaskDto> findTask(Long taskId) {
        return taskRepository.findTaskById(taskId);
    }

    @Override
    public boolean createTask(TaskRequest taskRequest) {
        Project project = projectRepository.getProjectById(taskRequest.getProjectId());
        Milestone milestone = milestoneRepository.findById(taskRequest.getMilestoneId()).orElse(null);
        Task task = Task.builder().name(taskRequest.getName()).project(project).milestone(milestone).build();

        taskRepository.save(task);
        return true;
    }

    @Override
    public boolean deleteTask(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteTaskById(taskId);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public List<CommentDto> findCommentListByTask(Long taskId) {
        return commentRepository.commentListByTaskId(taskId);
    }

    @Override
    public boolean createComment(CommentRequest commentRequest, Long taskId) {
        Task task = taskRepository.getTaskById(taskId);
        Comment comment = Comment.builder()
            .content(commentRequest.getContent())
            .task(task)
            .build();

        commentRepository.save(comment);

        return true;
    }

    @Override
    public boolean modifyComment(Long commentId, CommentModifyRequest commentModifyRequest) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment = Comment.builder().id(commentModifyRequest.getId()).content(commentModifyRequest.getContent())
                    .build();
            commentRepository.save(comment);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean modifyTask(Long taskId, TaskRequest taskRequest) {
        Project project = projectRepository.getProjectById(taskRequest.getProjectId());
        Task task = Task.builder()
            .name(taskRequest.getName())
            .project(project)
            .detail(taskRequest.getDetail())
            .build();
        taskRepository.save(task);
        return true;
    }

    @Override
    public boolean deleteComment(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            commentRepository.deleteById(taskId);
            return true;

        } else {
            return false;
        }

    }
}
