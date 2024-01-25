package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.ProjectDto;
import com.nhnacademy.minidooray.task.backend.domain.ProjectRegistryRequest;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import java.util.List;

public interface ProjectService {
    Project createProject(ProjectRegistryRequest projectRegistryRequest, String adminId);

    List<ProjectDto> getProjectListByAccountId(String accountId);

    ProjectDto getProjectDtoById(Long projectId);


}