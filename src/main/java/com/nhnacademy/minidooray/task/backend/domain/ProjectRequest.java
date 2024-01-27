package com.nhnacademy.minidooray.task.backend.domain;

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
    private String status;

    @NotNull
    private String adminId;

    @NotNull
    private String content;


}
