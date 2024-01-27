package com.nhnacademy.minidooray.task.backend.controller;

import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MilestoneDetailDto;
import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MilestoneDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.milestone.MilestoneRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.project.ProjectIdOnlyRequest;
import com.nhnacademy.minidooray.task.backend.service.interfaces.ProjectService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/milestone")
public class MilestoneController {
    private final ProjectService projectService;

    public MilestoneController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/list")
    public ResponseEntity<List<MilestoneDto>> getMilestoneList(@RequestBody ProjectIdOnlyRequest request) {
        return ResponseEntity.ok().body(projectService.getMilestoneByProject(request.getId()));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MilestoneDetailDto> getMilestone(@RequestParam("milestoneId") Long milestoneId) {
        Optional<MilestoneDetailDto> milestoneInfo =
                projectService.getMilestoneById(milestoneId);

        return milestoneInfo.isPresent()
                ? ResponseEntity.ok().body(milestoneInfo.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createMilestone(@Valid @RequestBody MilestoneRequest milestoneRequest,
                                                @PathVariable("projectId") Long projectId) {
        boolean isProcessed = projectService.createMileStone(milestoneRequest);

        return isProcessed
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping("/modify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updateMilestone(@RequestParam("milestoneId") Long id,
                                                @Valid @RequestBody MilestoneRequest milestoneRequest) {
        boolean isProcessed = projectService.updateMilestone(milestoneRequest, id);

        return isProcessed
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteMilestone(@RequestParam("MilestoneId") Long id) {

        boolean isProcessed = projectService.deleteMilestone(id);

        return isProcessed
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
