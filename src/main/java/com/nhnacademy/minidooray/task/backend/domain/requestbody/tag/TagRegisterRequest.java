package com.nhnacademy.minidooray.task.backend.domain.requestbody.tag;

import lombok.Data;

@Data
public class TagRegisterRequest {
    Long projectId;
    String name;
}
