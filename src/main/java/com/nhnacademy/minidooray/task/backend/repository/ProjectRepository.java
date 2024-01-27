package com.nhnacademy.minidooray.task.backend.repository;

import com.nhnacademy.minidooray.task.backend.domain.ProjectDto;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT new com.nhnacademy.minidooray.task.backend.domain.ProjectDto(p.id, p.name, p.status, p.content, p.adminId)  FROM Project p INNER JOIN ProjectMember m ON p.id = m.pk.projectId WHERE m.pk.accountId = :accountId")
    List<ProjectDto> getProjectListById(@Param("accountId") String accountId);

    Optional<ProjectDto> findProjectById(Long projectId);

    Optional<Project> getProjectById(Long projectId);



}
