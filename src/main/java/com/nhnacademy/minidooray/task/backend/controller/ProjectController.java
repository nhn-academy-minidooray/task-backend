package com.nhnacademy.minidooray.task.backend.controller;

import com.nhnacademy.minidooray.task.backend.domain.dto.project.ProjectDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.project.ProjectRequest;
import com.nhnacademy.minidooray.task.backend.service.interfaces.ProjectService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProjectDto>> getProjectListByAccountId(@RequestParam(name = "accountId") String accountId) {
        List<ProjectDto> list = projectService.getProjectListByAccountId(accountId);

        log.info(list.toString());
        return ResponseEntity.ok().body(list);
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