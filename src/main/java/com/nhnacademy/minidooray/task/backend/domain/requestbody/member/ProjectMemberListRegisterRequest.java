package com.nhnacademy.minidooray.task.backend.domain.requestbody.member;

import java.util.List;
import lombok.Data;

@Data
public class ProjectMemberListRegisterRequest {
    Long projectId;
    List<String> accountIdList;
}
