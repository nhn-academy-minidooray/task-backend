package com.nhnacademy.minidooray.task.backend.repository;

import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MemberIdOnlyDTO;
import com.nhnacademy.minidooray.task.backend.entity.ProjectMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMember.Pk> {
    List<MemberIdOnlyDTO> getProjectMembersByPk_ProjectId(Long projectId);
}
