package com.nhnacademy.minidooray.task.backend.repository;

import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MemberIdOnlyDTO;
import com.nhnacademy.minidooray.task.backend.entity.ProjectMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMember.Pk> {
    @Query("select new com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MemberIdOnlyDTO(p.pk.accountId) from ProjectMember p where p.pk.projectId = :projectId")
    List<MemberIdOnlyDTO> getProjectMembersByPk_ProjectId(Long projectId);
}
