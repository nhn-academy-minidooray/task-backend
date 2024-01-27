package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.dto.comment.CommentDto;
import com.nhnacademy.minidooray.task.backend.domain.dto.task.TaskDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentModifyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.task.TaskRequest;
import com.nhnacademy.minidooray.task.backend.entity.Comment;
import com.nhnacademy.minidooray.task.backend.entity.Milestone;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.Tag;
import com.nhnacademy.minidooray.task.backend.entity.Task;
import com.nhnacademy.minidooray.task.backend.entity.TaskTag;
import com.nhnacademy.minidooray.task.backend.repository.CommentRepository;
import com.nhnacademy.minidooray.task.backend.repository.MilestoneRepository;
import com.nhnacademy.minidooray.task.backend.repository.ProjectRepository;
import com.nhnacademy.minidooray.task.backend.repository.TagRepository;
import com.nhnacademy.minidooray.task.backend.repository.TaskRepository;
import com.nhnacademy.minidooray.task.backend.repository.TaskTagRepository;
import com.nhnacademy.minidooray.task.backend.service.interfaces.TaskService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final CommentRepository commentRepository;
    private final TaskTagRepository taskTagRepository;
    private final TagRepository tagRepository;

    public TaskServiceImpl(TaskRepository taskRepository, MilestoneRepository milestoneRepository,
                           ProjectRepository projectRepository, CommentRepository commentRepository,
                           TaskTagRepository taskTagRepository, TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.milestoneRepository = milestoneRepository;
        this.projectRepository = projectRepository;
        this.commentRepository = commentRepository;
        this.taskTagRepository = taskTagRepository;
        this.tagRepository = tagRepository;
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
        Optional<Project> optionalProject = projectRepository.getProjectById(taskRequest.getProjectId());
        Optional<Milestone> optionalMilestone = milestoneRepository.findById(taskRequest.getMilestoneId());

        if (optionalProject.isPresent() && optionalMilestone.isPresent()) {
            Project project = optionalProject.get();
            Milestone milestone = optionalMilestone.get();

            Task task = Task.builder()
                    .name(taskRequest.getName())
                    .project(project)
                    .milestone(milestone)
                    .build();
            Task saveTask = taskRepository.save(task);
            saveTaskTag(taskRequest, saveTask);

            return Objects.equals(task, saveTask);
        } else {
            return false;
        }
    }

    private void saveTaskTag(TaskRequest taskRequest, Task saveTask) {

        TaskTag.Pk pk;
        TaskTag taskTag;
        Tag tag;
        for (Long tagId : taskRequest.getTagList()) {
            Optional<Tag> byId = tagRepository.findById(tagId);
            if (byId.isPresent()) {
                tag = byId.get();
                pk = TaskTag.Pk.builder().tagId(tagId).taskId(saveTask.getId()).build();
                taskTag = TaskTag.builder().pk(pk).tag(tag).task(saveTask).build();
                taskTagRepository.save(taskTag);
            } else {
                //TODO exception
            }
        }
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
        Comment saveComment = commentRepository.save(comment);
        return Objects.equals(comment, saveComment);
    }

    @Override
    public boolean modifyComment(Long commentId, CommentModifyRequest commentModifyRequest) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.modify(commentModifyRequest.getContent());
            Comment modifyComment = commentRepository.save(comment);
            return Objects.equals(comment, modifyComment);
        } else {
            return false;
        }
    }

    @Override
    public boolean modifyTask(Long taskId, TaskRequest taskRequest) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            Milestone milestone = milestoneRepository.findById(taskRequest.getMilestoneId()).orElse(null);
            Task modifyTask = task.get();
            modifyTask.modify(taskRequest.getName(), milestone);

            Task saveTask = taskRepository.save(modifyTask);
            return Objects.equals(modifyTask, saveTask);
        } else {
            return false;
        }
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
