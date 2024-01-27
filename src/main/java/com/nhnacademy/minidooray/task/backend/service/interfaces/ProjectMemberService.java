package com.nhnacademy.minidooray.task.backend.service.interfaces;

import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MemberIdOnlyDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.member.ProjectMemberListRegisterRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.member.ProjectMemberRegisterRequest;
import java.util.List;

public interface ProjectMemberService {
    List<MemberIdOnlyDTO> getProjectMembers(Long projectId);

    boolean registerProjectMembers(ProjectMemberListRegisterRequest request);

    boolean registerProjectMember(ProjectMemberRegisterRequest request);

    boolean deleteProjectMember(ProjectMemberRegisterRequest request);
}
