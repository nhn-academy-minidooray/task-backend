package com.nhnacademy.minidooray.task.backend.controller;

import com.nhnacademy.minidooray.task.backend.domain.MilestoneDetailDto;
import com.nhnacademy.minidooray.task.backend.domain.MilestoneDto;
import com.nhnacademy.minidooray.task.backend.domain.MilestoneRequest;
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
@RequestMapping("/project/{projectId}/milestone")
public class MilestoneController {
    private final ProjectService projectService;

    public MilestoneController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<MilestoneDto> getMilestoneList(@PathVariable("projectId") Long id) {
        return projectService.getMilestoneByProject(id);
    }

    @GetMapping("/{milestoneId}")
    @ResponseStatus(HttpStatus.OK)
    public MilestoneDetailDto getMilestone(@PathVariable("projectId") Long projectId,
                                           @PathVariable("milestoneId") Long milestoneId) {
        return projectService.getMilestoneByProjectIdAndMilestoneId(projectId, milestoneId);
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMilestone(@Valid @RequestBody MilestoneRequest milestoneRequest,
                                @PathVariable("projectId") Long projectId) {
        projectService.createMileStone(milestoneRequest, projectId);
    }

    @PutMapping("/{id}/modify")
    @ResponseStatus(HttpStatus.OK)
    public void updateMilestone(@PathVariable("projectId") Long projectId, @PathVariable("id") Long id,
                                @Valid @RequestBody MilestoneRequest milestoneRequest) {
        projectService.updateMilestone(milestoneRequest, id);
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMilestone(@PathVariable("id") Long id) {
        projectService.deleteMilestone(id);
    }

}