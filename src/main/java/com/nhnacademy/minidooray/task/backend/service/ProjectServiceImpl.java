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
import java.util.stream.Collectors;
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
    public void createProject(ProjectRequest projectRequest) {
        Project project = new Project(projectRequest.getName(), Status.ACTIVATION.getValue(),
                projectRequest.getAdminId());
        projectRepository.save(project);
    }

    @Override
    public List<ProjectDto> getProjectListByAccountId(String accountId) {
        List<List<Object>> projectListById = projectRepository.getProjectListById(accountId);

        return projectListById.stream()
                .map(objects ->
                        new ProjectDto() {
                            @Override
                            public Long getId() {
                                return (Long) objects.get(0);
                            }

                            @Override
                            public String getName() {
                                return (String) objects.get(1);
                            }
                        })
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDto getProjectDtoById(Long projectId) {
        return projectRepository.findProjectById(projectId);
    }

    @Override
    public void createMileStone(MilestoneRequest milestoneRequest, Long projectId) {
        Project projectById = projectRepository.getProjectById(projectId);
        Milestone milestone = new Milestone(
                milestoneRequest.getName(),
                milestoneRequest.getStartDate(),
                milestoneRequest.getEndDate(),
                "N", projectById);

        milestoneRepository.saveAndFlush(milestone);
    }

    @Override
    public List<MilestoneDto> getMilestoneList() {
        return milestoneRepository.findAll()
                .stream()
                .map(milestone -> new MilestoneDto() {
                    @Override
                    public Long getId() {
                        return milestone.getId();
                    }

                    @Override
                    public String getName() {
                        return milestone.getName();
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MilestoneDto> getMilestoneByProject(Long projectId) {
        List<List<Object>> mileStoneByProjectId = milestoneRepository.findMileStoneByProjectId(projectId);
        return mileStoneByProjectId.stream()
                .map(objects -> new MilestoneDto() {
                    @Override
                    public Long getId() {
                        return (Long) objects.get(0);
                    }

                    @Override
                    public String getName() {
                        return (String) objects.get(1);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public MilestoneDetailDto getMilestoneByProjectIdAndMilestoneId(Long projectId, Long milestoneId) {
        return milestoneRepository.findMilestoneByProjectIdAndMilestoneId(projectId, milestoneId);
    }

    @Override
    public MilestoneDetailDto getMilestoneById(Long milestoneId) {
        return milestoneRepository.findMilestoneById(milestoneId).orElse(null);
    }

    @Override
    public void updateMilestone(MilestoneRequest milestoneRequest, Long milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId).orElse(null);
        if (Objects.isNull(milestone)) {
            return;
        } else {
            Milestone modify = milestone.modify(milestoneRequest.getName(), milestoneRequest.getStartDate(),
                    milestoneRequest.getEndDate());
            milestoneRepository.save(modify);
        }
    }


    @Override
    public void deleteMilestone(Long milestoneId) {
        milestoneRepository.deleteMilestoneById(milestoneId);
    }


}