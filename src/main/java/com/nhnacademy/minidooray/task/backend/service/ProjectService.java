package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.MilestoneDto;
import com.nhnacademy.minidooray.task.backend.domain.MilestoneRequest;
import com.nhnacademy.minidooray.task.backend.domain.ProjectDto;
import com.nhnacademy.minidooray.task.backend.domain.ProjectRequest;
import java.util.List;

public interface ProjectService {
    void createProject(ProjectRequest projectRequest);

    List<ProjectDto> getProjectListByAccountId(String accountId);

    ProjectDto getProjectDtoById(Long projectId);

    void createMileStone(MilestoneRequest milestoneRequest);

    List<MilestoneDto> getMilestoneList();

    MilestoneDto getMilestoneById(Long milestoneId);

    void deleteMilestone(Long milestoneId);

}