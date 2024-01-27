package com.nhnacademy.minidooray.task.backend.controller;

import com.nhnacademy.minidooray.task.backend.domain.ProjectIdOnlyRequest;
import com.nhnacademy.minidooray.task.backend.domain.TaskDto;
import com.nhnacademy.minidooray.task.backend.domain.TaskRequest;
import com.nhnacademy.minidooray.task.backend.service.TaskService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<TaskDto>> taskDtoList(@RequestBody ProjectIdOnlyRequest projectId) {
        return ResponseEntity.ok().body(taskService.findTaskListByProject(projectId.getId()));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskDto> findTaskDto(@RequestParam("taskId") Long taskId) {
        Optional<TaskDto> info = taskService.findTask(taskId);

        return info.isPresent()
                ? ResponseEntity.ok().body(info.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        boolean isProcessed = taskService.createTask(taskRequest);

        return isProcessed
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping("/modify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> modifyTask(@RequestParam("taskId") Long taskId,
                                           @Valid @RequestBody TaskRequest taskRequest) {
        boolean isProcessed = taskService.modifyTask(taskId, taskRequest);

        return isProcessed
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@RequestParam("taskId") Long taskId) {
        taskService.deleteTask(taskId);
    }
}
