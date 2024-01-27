package com.nhnacademy.minidooray.task.backend.domain.requestbody;

import lombok.Data;

@Data
public class TagRegisterRequest {
    Long projectId;
    String name;
}
