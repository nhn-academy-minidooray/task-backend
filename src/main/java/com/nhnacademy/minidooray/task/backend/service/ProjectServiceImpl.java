package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.MilestoneDetailDto;
import com.nhnacademy.minidooray.task.backend.domain.MilestoneDto;
import com.nhnacademy.minidooray.task.backend.domain.MilestoneRequest;
import com.nhnacademy.minidooray.task.backend.domain.ProjectDto;
import com.nhnacademy.minidooray.task.backend.domain.ProjectRequest;
import com.nhnacademy.minidooray.task.backend.domain.Status;
import com.nhnacademy.minidooray.task.backend.entity.Milestone;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.ProjectMember;
import com.nhnacademy.minidooray.task.backend.repository.MilestoneRepository;
import com.nhnacademy.minidooray.task.backend.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray.task.backend.repository.ProjectRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    private final MilestoneRepository milestoneRepository;

    private final ProjectMemberRepository projectMemberRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, MilestoneRepository milestoneRepository,
                              ProjectMemberRepository projectMemberRepository) {
        this.projectRepository = projectRepository;
        this.milestoneRepository = milestoneRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    @Override
    public boolean createProject(ProjectRequest projectRequest) {
        Project project = Project.builder()
                .name(projectRequest.getName())
                .status(Status.ACTIVATION.getValue())
                .adminId(projectRequest.getAdminId())
                .content(projectRequest.getContent())
                .build();

        Project save = projectRepository.save(project);
        ProjectMember.Pk pk = new ProjectMember.Pk(project.getAdminId(), project.getId());
        ProjectMember projectMember = ProjectMember.builder().pk(pk).project(save).build();


        return Objects.nonNull(save) && Objects.nonNull(projectMember) && Objects.equals(project, save) ? true : false;
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

        Milestone saveMilestone = milestoneRepository.save(milestone);
        return Objects.equals(milestone, saveMilestone);
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
        Optional<Milestone> milestone = milestoneRepository.findById(milestoneId);
        if (milestone.isPresent()) {
            Milestone getMilestone = milestone.get();
            getMilestone.modify(milestoneRequest.getName(), milestoneRequest.getStartDate(),
                    milestoneRequest.getEndDate());
            Milestone saveMilestone = milestoneRepository.save(getMilestone);
            return Objects.equals(getMilestone, saveMilestone);
        } else {

            return false;
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

    @Override
    public Optional<MilestoneDto> getMilestoneByTask(Long taskId) {
        return milestoneRepository.findMileStoneByTaskId(taskId);
    }
}