package com.nhnacademy.minidooray.task.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    Long projectId;
    String name;
    Long milestoneId;
}
