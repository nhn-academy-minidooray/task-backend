package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.dto.MemberIdOnlyDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.ProjectIdOnlyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.ProjectMemberListRegisterRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.ProjectMemberRegisterRequest;
import java.util.List;

public interface ProjectMemberService {
    List<MemberIdOnlyDTO> getProjectMembers(ProjectIdOnlyRequest request);

    boolean registerProjectMembers(ProjectMemberListRegisterRequest request);

    boolean registerProjectMember(ProjectMemberRegisterRequest request);

    boolean deleteProjectMember(ProjectMemberRegisterRequest request);
}
