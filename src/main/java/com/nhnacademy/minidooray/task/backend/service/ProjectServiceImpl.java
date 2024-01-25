package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.ProjectDto;
import com.nhnacademy.minidooray.task.backend.domain.ProjectRegistryRequest;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.repository.ProjectRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @Override
    public Project createProject(ProjectRegistryRequest projectRegistryRequest, String adminId) {
        Project project = new Project();
        project.setProjectName(projectRegistryRequest.getProjectName());
        project.setProjectStatus("활성");
        project.setProjectAdminId(adminId);
        return projectRepository.save(project);
    }

    @Override
    public List<ProjectDto> getProjectListByAccountId(String accountId) {
        List<List<Object>> projectListById = projectRepository.getProjectListById(accountId);

        return projectListById.stream()
                .map(objects ->
                        new ProjectDto() {
                            @Override
                            public Long getProjectId() {
                                return (Long) objects.get(0);
                            }

                            @Override
                            public String getProjectName() {
                                return (String) objects.get(1);
                            }
                        })
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDto getProjectDtoById(Long projectId) {
        return projectRepository.findProjectByProjectId(projectId);

    }
}