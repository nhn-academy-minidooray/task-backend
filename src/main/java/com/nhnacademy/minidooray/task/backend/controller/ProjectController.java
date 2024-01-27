package com.nhnacademy.minidooray.task.backend.controller;

import com.nhnacademy.minidooray.task.backend.domain.ProjectDto;
import com.nhnacademy.minidooray.task.backend.domain.ProjectListRequest;
import com.nhnacademy.minidooray.task.backend.domain.ProjectRequest;
import com.nhnacademy.minidooray.task.backend.service.ProjectService;
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
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/list/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectDto> projectList(@PathVariable("accountId") String accountId) {
        return projectService.getProjectListByAccountId(accountId);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(@RequestBody ProjectRequest projectRequest) {
        projectService.createProject(projectRequest);
    }

    @GetMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDto getProject(@PathVariable("projectId") Long projectId) {
        return projectService.getProjectDtoById(projectId);
    }

    @PutMapping("/{projectId}/modify")
    public void modifyProject(@PathVariable("projectId") Long projectId){
        //TODO: 수정할때 필요한 modifyProjectRequest Class 필요
    }

    @DeleteMapping("/{projectId}/delete")
    public void deleteProject(@PathVariable("projectId") Long projectId){
        //TODO
    }
}