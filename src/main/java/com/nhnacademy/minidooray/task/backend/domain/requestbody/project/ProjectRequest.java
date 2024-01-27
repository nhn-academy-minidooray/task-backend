package com.nhnacademy.minidooray.task.backend.domain.requestbody.project;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {

    @NotNull
    private String name;

    @NotNull
    private String detail;

    @NotNull
    private String adminId;
}
