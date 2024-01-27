package com.nhnacademy.minidooray.task.backend.controller;

import com.nhnacademy.minidooray.task.backend.domain.ProjectDto;

import com.nhnacademy.minidooray.task.backend.domain.ProjectRequest;
import com.nhnacademy.minidooray.task.backend.service.ProjectService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProjectDto>> projectList(@RequestParam("accountId") String accountId) {
        return ResponseEntity.ok().body(projectService.getProjectListByAccountId(accountId));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createProject(@RequestBody ProjectRequest projectRequest) {
        boolean isProcessed = projectService.createProject(projectRequest);

        return isProcessed
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable("projectId") Long projectId) {
        Optional<ProjectDto> projectInfo = projectService.getProjectDtoById(projectId);

        return projectInfo.isPresent()
                ? ResponseEntity.ok().body(projectInfo.get())
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}