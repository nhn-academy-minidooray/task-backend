package com.nhnacademy.minidooray.task.backend.controller;

import com.nhnacademy.minidooray.task.backend.domain.TaskDto;
import com.nhnacademy.minidooray.task.backend.domain.TaskRequest;
import com.nhnacademy.minidooray.task.backend.service.TaskService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project/{projectId}/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> taskDtoList(@PathVariable("projectId") Long projectId) {
        return taskService.findTaskListByProject(projectId);
    }

    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto findTaskDto(@PathVariable("projectId") Long projectId,
                               @PathVariable("taskId") Long taskId) {
        return taskService.findTask(projectId, taskId);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@PathVariable("projectId") Long projectId, @Valid @RequestBody TaskRequest taskRequest) {
        taskService.createTask(taskRequest, projectId);
    }

    @PutMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyTask(@PathVariable("taskId") Long taskId) {

    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
    }
}
