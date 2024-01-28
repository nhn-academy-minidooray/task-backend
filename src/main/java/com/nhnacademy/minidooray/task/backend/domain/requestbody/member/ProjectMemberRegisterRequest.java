package com.nhnacademy.minidooray.task.backend.domain.requestbody.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectMemberRegisterRequest {
    private Long projectId;
    private String accountId;
}
