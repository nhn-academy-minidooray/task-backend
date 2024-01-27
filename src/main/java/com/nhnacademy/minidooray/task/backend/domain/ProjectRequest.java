package com.nhnacademy.minidooray.task.backend.domain;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectRequest {

    @NotNull
    private String name;

    @NotNull
    private String adminId;

}
