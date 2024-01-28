package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.Status;
import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MilestoneDetailDto;
import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MilestoneDto;
import com.nhnacademy.minidooray.task.backend.domain.dto.project.ProjectDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.milestone.MilestoneRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.project.ProjectRequest;
import com.nhnacademy.minidooray.task.backend.entity.Milestone;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.ProjectMember;
import com.nhnacademy.minidooray.task.backend.exception.ProjectCreationFailedException;
import com.nhnacademy.minidooray.task.backend.exception.ProjectMemberAddFailedException;
import com.nhnacademy.minidooray.task.backend.repository.MilestoneRepository;
import com.nhnacademy.minidooray.task.backend.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray.task.backend.repository.ProjectRepository;
import com.nhnacademy.minidooray.task.backend.service.interfaces.ProjectService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
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
                .adminId(projectRequest.getAdminId())
                .status(Status.ACTIVATION.getValue())
                .detail(projectRequest.getDetail())
                .build();

        Project savedProject = projectRepository.save(project);
        if (!Objects.equals(project, savedProject)) {
            throw new ProjectCreationFailedException("프로젝트 생성 중 오류가 발생하였습니다");
        }
        ProjectMember.Pk projectMemberPk = new ProjectMember.Pk(savedProject.getAdminId(), savedProject.getId());
        ProjectMember projectMember = ProjectMember.builder()
                .pk(projectMemberPk)
                .project(savedProject)
                .build();

        ProjectMember savedProjectMember = projectMemberRepository.save(projectMember);
        if (!Objects.equals(projectMember, savedProjectMember)) {
            throw new ProjectMemberAddFailedException("멤버 등록 중 오류가 발생하였습니다");
        }

        return true;
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
        Optional<Project> projectById = projectRepository.getProjectById(milestoneRequest.getProjectId());

        if (projectById.isPresent()) {
            Milestone milestone = Milestone.builder()
                    .name(milestoneRequest.getName())
                    .startDate(milestoneRequest.getStartDate())
                    .endDate(milestoneRequest.getEndDate())
                    .overOrNot("N").project(projectById.get()).build();
            Milestone saveMilestone = milestoneRepository.save(milestone);
            return Objects.equals(milestone, saveMilestone);
        } else {
            return false;
        }


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