package com.nhnacademy.minidooray.task.backend.domain.requestbody.task;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequest {
    private Long projectId;

    private String name;

    private Long milestoneId;

    private List<Long> tagList;

    private String detail;
}
