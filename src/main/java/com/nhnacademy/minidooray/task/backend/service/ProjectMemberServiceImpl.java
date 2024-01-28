package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MemberIdOnlyDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.member.ProjectMemberRegisterRequest;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.ProjectMember;
import com.nhnacademy.minidooray.task.backend.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray.task.backend.repository.ProjectRepository;
import com.nhnacademy.minidooray.task.backend.service.interfaces.ProjectMemberService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {
    private final ProjectMemberRepository projectMemberRepository;

    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    @Override
    public List<MemberIdOnlyDTO> getProjectMembers(Long projectId) {
        return projectMemberRepository.getProjectMembersByPk_ProjectId(projectId);
    }

    @Transactional
    @Override
    public boolean registerProjectMember(ProjectMemberRegisterRequest request) {
        Optional<Project> project = projectRepository.findById(request.getProjectId());

        if(project.isPresent()){
            ProjectMember.Pk pk = new ProjectMember.Pk(request.getAccountId(), request.getProjectId());

            if (projectMemberRepository.existsById(pk)) {
                return false;
            }

            ProjectMember projectMember = new ProjectMember(pk, project.get());
            projectMemberRepository.save(projectMember);

            return true;
        }

        return false;
    }

    @Transactional
    @Override
    public boolean deleteProjectMember(ProjectMemberRegisterRequest request) {
        ProjectMember.Pk pk = new ProjectMember.Pk(request.getAccountId(), request.getProjectId());
        Optional<ProjectMember> member = projectMemberRepository.findById(pk);

        if(member.isPresent()){
            projectMemberRepository.deleteById(pk);

            return true;
        }

        return false;
    }
}
