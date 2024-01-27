package com.nhnacademy.minidooray.task.backend.controller;

import com.nhnacademy.minidooray.task.backend.domain.MilestoneDetailDto;
import com.nhnacademy.minidooray.task.backend.domain.MilestoneDto;
import com.nhnacademy.minidooray.task.backend.domain.MilestoneRequest;
import com.nhnacademy.minidooray.task.backend.domain.ProjectIdOnlyRequest;
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
@RequestMapping("/milestone")
public class MilestoneController {
    private final ProjectService projectService;

    public MilestoneController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<MilestoneDto> getMilestoneList(@RequestBody ProjectIdOnlyRequest request) {
        return projectService.getMilestoneByProject(request.getId());
    }

    @GetMapping("/{milestoneId}")
    @ResponseStatus(HttpStatus.OK)
    public MilestoneDetailDto getMilestone(@PathVariable("milestoneId") Long milestoneId) {
        // TODO : milestone id만으로 milestone get하는 메서드 필요
//        return projectService.getMilestoneByProjectIdAndMilestoneId(projectId, milestoneId);
        return null;
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMilestone(@Valid @RequestBody MilestoneRequest milestoneRequest) {
        //TODO: milestonerequest안에 projectId를 일단 넣어놨는데 createMilestone이 milestoneRequest 하나만 parameter로 받아도 될것 같습니다.
        projectService.createMileStone(milestoneRequest, milestoneRequest.getProjectId());
    }

    @PutMapping("/{id}/modify")
    @ResponseStatus(HttpStatus.OK)
    public void updateMilestone(@PathVariable("id") Long id,
                                @Valid @RequestBody MilestoneRequest milestoneRequest) {
        // TODO : milestone id만으로 modify 할수 있을 것 같습니다.
        projectService.updateMilestone(milestoneRequest, id);
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMilestone(@PathVariable("id") Long id) {
        projectService.deleteMilestone(id);
    }

}
