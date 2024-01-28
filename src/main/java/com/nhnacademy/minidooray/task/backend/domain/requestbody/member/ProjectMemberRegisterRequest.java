package com.nhnacademy.minidooray.task.backend.domain.requestbody.member;

import lombok.Data;

@Data
public class ProjectMemberRegisterRequest {
    Long projectId;
    String accountId;
}
