package com.nhnacademy.minidooray.task.backend.domain.requestbody;

import lombok.Data;

@Data
public class ProjectMemberRegisterRequest {
    Long projectId;
    String accountId;
}
