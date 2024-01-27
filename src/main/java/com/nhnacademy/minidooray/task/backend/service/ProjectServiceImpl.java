package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.MilestoneDetailDto;
import com.nhnacademy.minidooray.task.backend.domain.MilestoneDto;
import com.nhnacademy.minidooray.task.backend.domain.MilestoneRequest;
import com.nhnacademy.minidooray.task.backend.domain.ProjectDto;
import com.nhnacademy.minidooray.task.backend.domain.ProjectRequest;
import com.nhnacademy.minidooray.task.backend.domain.Status;
import com.nhnacademy.minidooray.task.backend.entity.Milestone;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.repository.MilestoneRepository;
import com.nhnacademy.minidooray.task.backend.repository.ProjectRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    private final MilestoneRepository milestoneRepository;


    public ProjectServiceImpl(ProjectRepository projectRepository, MilestoneRepository milestoneRepository) {
        this.projectRepository = projectRepository;
        this.milestoneRepository = milestoneRepository;
    }

    @Override
    public boolean createProject(ProjectRequest projectRequest) {
        Project project = Project.builder()
                .name(projectRequest.getName())
                .status(Status.ACTIVATION.getValue())
                .adminId(projectRequest.getAdminId())
                .build();

        Project save = projectRepository.save(project);
        return Objects.nonNull(save) ? true : false;
    }

    @Override
    public List<ProjectDto> getProjectListByAccountId(String accountId) {

        return projectRepository.getProjectListById(accountId);
    }

    @Override
    public Optional<ProjectDto> getProjectDtoById(Long projectId) {
        return projectRepository.findProjectById(projectId);
    }

    @Override
    public boolean createMileStone(MilestoneRequest milestoneRequest) {
        Project projectById = projectRepository.getProjectById(milestoneRequest.getProjectId());
        Milestone milestone = Milestone.builder()
                .name(milestoneRequest.getName())
                .startDate(milestoneRequest.getStartDate())
                .endDate(milestoneRequest.getEndDate())
                .overOrNot("N").project(projectById).build();

        milestoneRepository.save(milestone);
        return true;
    }


    @Override
    public List<MilestoneDto> getMilestoneByProject(Long projectId) {
        return milestoneRepository.findMileStoneByProjectId(projectId);
    }

    @Override
    public Optional<MilestoneDetailDto> getMilestoneById(Long milestoneId) {
        return milestoneRepository.findMilestoneById(milestoneId);
    }

    @Override
    public boolean updateMilestone(MilestoneRequest milestoneRequest, Long milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId).orElse(null);
        if (Objects.isNull(milestone)) {
            return false;
        } else {
            Milestone modify = milestone.modify(milestoneRequest.getName(), milestoneRequest.getStartDate(),
                    milestoneRequest.getEndDate());
            milestoneRepository.save(modify);
            return true;
        }
    }

    @Override
    public boolean deleteMilestone(Long milestoneId) {
        if (milestoneRepository.existsById(milestoneId)) {
            milestoneRepository.deleteMilestoneById(milestoneId);
            return true;
        } else {
            return false;
        }
    }
}