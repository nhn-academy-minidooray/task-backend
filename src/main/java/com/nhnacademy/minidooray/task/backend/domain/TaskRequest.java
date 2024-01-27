package com.nhnacademy.minidooray.task.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    private Long projectId;
    private String name;
    private String detail;
    private Long milestoneId;
}
