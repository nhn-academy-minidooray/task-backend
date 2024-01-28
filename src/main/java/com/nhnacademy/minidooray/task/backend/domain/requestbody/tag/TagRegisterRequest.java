package com.nhnacademy.minidooray.task.backend.domain.requestbody.tag;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagRegisterRequest {
    Long projectId;
    String name;
}
