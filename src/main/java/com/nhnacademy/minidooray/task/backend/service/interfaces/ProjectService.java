package com.nhnacademy.minidooray.task.backend.service.interfaces;


import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MilestoneDetailDto;
import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MilestoneDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.milestone.MilestoneRequest;
import com.nhnacademy.minidooray.task.backend.domain.dto.project.ProjectDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.project.ProjectRequest;
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

}