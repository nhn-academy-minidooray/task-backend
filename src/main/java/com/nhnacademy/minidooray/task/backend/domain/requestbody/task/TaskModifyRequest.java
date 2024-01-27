package com.nhnacademy.minidooray.task.backend.domain.requestbody.task;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskModifyRequest {
    private Long projectId;

    private String name;

    private Long milestoneId;

    private List<Long> tagList;

    private String detail;


}
