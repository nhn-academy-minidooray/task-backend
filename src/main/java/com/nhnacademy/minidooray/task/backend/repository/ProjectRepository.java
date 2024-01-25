package com.nhnacademy.minidooray.task.backend.repository;

import com.nhnacademy.minidooray.task.backend.domain.ProjectDto;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p.projectId, p.projectName FROM Project p INNER JOIN ProjectMember m ON p.projectId = m.pk.projectId WHERE m.pk.accountId = :accountId")
    List<List<Object>> getProjectListById(@Param("accountId") String accountId);

    ProjectDto findProjectByProjectId(Long projectId);

}