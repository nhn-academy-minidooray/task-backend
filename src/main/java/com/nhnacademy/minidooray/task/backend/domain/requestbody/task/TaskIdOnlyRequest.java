package com.nhnacademy.minidooray.task.backend.domain.requestbody.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskIdOnlyRequest {
    private Long id;
}
