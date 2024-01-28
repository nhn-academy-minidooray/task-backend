package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.dto.comment.CommentDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentRequest;
import com.nhnacademy.minidooray.task.backend.domain.dto.task.TaskDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.task.TaskRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentModifyRequest;
import com.nhnacademy.minidooray.task.backend.entity.Comment;
import com.nhnacademy.minidooray.task.backend.entity.Milestone;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.Task;
import com.nhnacademy.minidooray.task.backend.repository.CommentRepository;
import com.nhnacademy.minidooray.task.backend.repository.MilestoneRepository;
import com.nhnacademy.minidooray.task.backend.repository.ProjectRepository;
import com.nhnacademy.minidooray.task.backend.repository.TaskRepository;
import com.nhnacademy.minidooray.task.backend.service.interfaces.TaskService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final MilestoneRepository milestoneRepository;

    private final ProjectRepository projectRepository;
    public TaskServiceImpl(TaskRepository taskRepository, MilestoneRepository milestoneRepository,
                           ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.milestoneRepository = milestoneRepository;
        this.projectRepository = projectRepository;
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
}
