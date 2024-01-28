package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.dto.comment.CommentDto;
import com.nhnacademy.minidooray.task.backend.domain.dto.task.TaskDto;
import com.nhnacademy.minidooray.task.backend.domain.dto.task.TaskInfoResponseDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentModifyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.task.TaskRequest;
import com.nhnacademy.minidooray.task.backend.entity.Comment;
import com.nhnacademy.minidooray.task.backend.entity.Milestone;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.Tag;
import com.nhnacademy.minidooray.task.backend.entity.Task;
import com.nhnacademy.minidooray.task.backend.entity.TaskTag;
import com.nhnacademy.minidooray.task.backend.exception.TagRegisterFailedException;
import com.nhnacademy.minidooray.task.backend.repository.CommentRepository;
import com.nhnacademy.minidooray.task.backend.repository.MilestoneRepository;
import com.nhnacademy.minidooray.task.backend.repository.ProjectRepository;
import com.nhnacademy.minidooray.task.backend.repository.TagRepository;
import com.nhnacademy.minidooray.task.backend.repository.TaskRepository;
import com.nhnacademy.minidooray.task.backend.repository.TaskTagRepository;
import com.nhnacademy.minidooray.task.backend.service.interfaces.TaskService;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final TaskTagRepository taskTagRepository;
    private final TagRepository tagRepository;

    public TaskServiceImpl(TaskRepository taskRepository, MilestoneRepository milestoneRepository,
                           ProjectRepository projectRepository,
                           TaskTagRepository taskTagRepository, TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.milestoneRepository = milestoneRepository;
        this.projectRepository = projectRepository;
        this.taskTagRepository = taskTagRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskInfoResponseDTO> findTaskListByProject(Long projectId) {
        List<List<Object>> nativeTaskList = taskRepository.nativeTaskList(projectId);
        return nativeTaskList.stream().map(list ->
            {
                return new TaskInfoResponseDTO(((BigInteger) list.get(0)).longValue(), // task id
                    (String) list.get(1),
                    (String) list.get(2),
                    Objects.nonNull(list.get(3)) ? (String) list.get(3) : Strings.EMPTY,
                    Objects.nonNull(list.get(4)) ? (String) list.get(4) : Strings.EMPTY,
                    Objects.nonNull(list.get(5)) ? ((BigInteger) list.get(5)).longValue() : null,
                    Objects.nonNull(list.get(6)) ? (String) list.get(6) : null
                );
            })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TaskDto> findTask(Long taskId) {
        return taskRepository.findTaskById(taskId);
    }

    @Transactional
    @Override
    public boolean registerTaskAndTaskTag(TaskRequest taskRequest) {
        Optional<Project> optionalProject = projectRepository.getProjectById(taskRequest.getProjectId());
        Milestone milestone = null;
        if(Objects.nonNull(taskRequest.getMilestoneId())) {
            milestone = milestoneRepository.findById(taskRequest.getMilestoneId())
                .orElse(null);
        }

        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            Task task = Task.builder()
                .name(taskRequest.getName())
                .project(project)
                .milestone(milestone)
                .detail(taskRequest.getDetail())
                .build();
            Task saveTask = taskRepository.save(task);
            if (Objects.nonNull(taskRequest.getTagList())) {
                for (Long tagId : taskRequest.getTagList()) {
                    Optional<Tag> byId = tagRepository.findById(tagId);
                    if (byId.isPresent()) {
                        Tag tag = byId.get();
                        TaskTag.Pk pk = new TaskTag.Pk(tag.getId(), saveTask.getId());
                        TaskTag taskTag = new TaskTag(pk, tag, saveTask);
                        taskTagRepository.save(taskTag);
                    } else {
                        throw new TagRegisterFailedException("태그 저장에 실패하였습니다.");
                    }
                }
                return Objects.equals(task, saveTask);
            }
        }
        return true;
    }

    @Transactional
    @Override
    public boolean deleteTask(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteTaskById(taskId);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
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
}
