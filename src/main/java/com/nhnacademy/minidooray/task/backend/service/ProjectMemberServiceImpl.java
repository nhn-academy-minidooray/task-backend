package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.dto.MemberIdOnlyDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.ProjectIdOnlyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.ProjectMemberListRegisterRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.ProjectMemberRegisterRequest;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.ProjectMember;
import com.nhnacademy.minidooray.task.backend.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray.task.backend.repository.ProjectRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService{
    private final ProjectMemberRepository projectMemberRepository;

    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    @Override
    public List<MemberIdOnlyDTO> getProjectMembers(ProjectIdOnlyRequest request) {
        return projectMemberRepository.getProjectMembersByPk_ProjectId(request.getId());
    }

    @Transactional
    @Override
    public boolean registerProjectMembers(ProjectMemberListRegisterRequest request) {
        Optional<Project> project = projectRepository.findById(request.getProjectId());

        if(project.isPresent()){
            List<ProjectMember> memberList = new ArrayList<>();

            request.getAccountIdList().forEach(accountId -> {
                ProjectMember.Pk pk = new ProjectMember.Pk(accountId, request.getProjectId());
                ProjectMember projectMember = new ProjectMember(pk, project.get());
                memberList.add(projectMember);
            });

            projectMemberRepository.saveAll(memberList);

            return true;
        }

        return false;
    }

    @Transactional
    @Override
    public boolean registerProjectMember(ProjectMemberRegisterRequest request) {
        Optional<Project> project = projectRepository.findById(request.getProjectId());

        if(project.isPresent()){
            ProjectMember.Pk pk = new ProjectMember.Pk(request.getAccountId(), request.getProjectId());
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
