package com.nhnacademy.minidooray.task.backend.service;


import com.nhnacademy.minidooray.task.backend.domain.MilestoneDetailDto;
import com.nhnacademy.minidooray.task.backend.domain.MilestoneDto;
import com.nhnacademy.minidooray.task.backend.domain.MilestoneRequest;
import com.nhnacademy.minidooray.task.backend.domain.ProjectDto;
import com.nhnacademy.minidooray.task.backend.domain.ProjectRequest;
import java.util.List;
import java.util.Optional;

public interface ProjectService {
    boolean createProject(ProjectRequest projectRequest);

    List<ProjectDto> getProjectListByAccountId(String accountId);

    Optional<ProjectDto> getProjectDtoById(Long projectId);

    boolean createMileStone(MilestoneRequest milestoneRequest);

    List<MilestoneDto> getMilestoneByProject(Long projectId);

    Optional<MilestoneDetailDto> getMilestoneById(Long milestoneId);

    boolean updateMilestone(MilestoneRequest milestoneRequest, Long milestoneId);

    boolean deleteMilestone(Long milestoneId);

    Optional<MilestoneDto> getMilestoneByTask(Long taskId);

}