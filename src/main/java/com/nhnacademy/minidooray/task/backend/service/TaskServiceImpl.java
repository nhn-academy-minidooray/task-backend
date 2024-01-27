package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.CommentDto;
import com.nhnacademy.minidooray.task.backend.domain.CommentRequest;
import com.nhnacademy.minidooray.task.backend.domain.TaskDto;
import com.nhnacademy.minidooray.task.backend.domain.TaskRequest;
import com.nhnacademy.minidooray.task.backend.entity.Comment;
import com.nhnacademy.minidooray.task.backend.entity.Milestone;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.Task;
import com.nhnacademy.minidooray.task.backend.repository.CommentRepository;
import com.nhnacademy.minidooray.task.backend.repository.MilestoneRepository;
import com.nhnacademy.minidooray.task.backend.repository.ProjectRepository;
import com.nhnacademy.minidooray.task.backend.repository.TaskRepository;
import java.util.List;
import java.util.stream.Collectors;
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
        return taskRepository.taskListByProject(projectId)
                .stream()
                .map(objects -> new TaskDto() {
                    @Override
                    public Long getId() {
                        return (Long) objects.get(0);
                    }

                    @Override
                    public String getName() {
                        return (String) objects.get(1);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto findTask(Long projectId, Long taskId) {
        return taskRepository.findTaskByProjectIdAndId(projectId, taskId);
    }

    @Override
    public void createTask(TaskRequest taskRequest, Long projectId) {
        Project project = projectRepository.getProjectById(projectId);
        Milestone milestone = milestoneRepository.findById(taskRequest.getMilestoneId()).orElse(null);
        Task task = new Task(taskRequest.getName(), project, milestone);

        taskRepository.saveAndFlush(task);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteTaskById(taskId);
    }

    @Override
    public List<CommentDto> findCommentListByTask(Long taskId) {
        return commentRepository.commentListByTaskId(taskId)
                .stream()
                .map(objects -> new CommentDto() {
                    @Override
                    public Long getId() {
                        return (Long) objects.get(0);
                    }

                    @Override
                    public String getContent() {
                        return (String) objects.get(1);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public void createComment(CommentRequest commentRequest, Long taskId) {
        Task task = taskRepository.getTaskById(taskId);
        Comment comment = new Comment(commentRequest.getName(), task);

        commentRepository.saveAndFlush(comment);
    }

    @Override
    public void deleteComment(Long taskId) {
        commentRepository.deleteById(taskId);
    }
}
