package com.nhnacademy.minidooray.task.backend.controller;

import com.nhnacademy.minidooray.task.backend.domain.ProjectDto;
import com.nhnacademy.minidooray.task.backend.domain.ProjectRegistryRequest;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.service.ProjectService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/list")
    public ResponseEntity<List<ProjectDto>> projectList() {
        List<ProjectDto> projectListByAccountId = projectService.getProjectListByAccountId("");

        return ResponseEntity.ok().body(projectListByAccountId);
    }

    @PostMapping("/register")
    public ResponseEntity<Project> createProject(@RequestBody ProjectRegistryRequest projectRegistryRequest) {
        Project project = projectService.createProject(projectRegistryRequest, "");

        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable("id") Long projectId) {
        ProjectDto projectDto = projectService.getProjectDtoById(projectId);

        return ResponseEntity.ok().body(projectDto);
    }

}