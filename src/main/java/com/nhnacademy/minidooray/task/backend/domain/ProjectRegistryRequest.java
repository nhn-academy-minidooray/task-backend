package com.nhnacademy.minidooray.task.backend.domain;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectRegistryRequest {

    @NotNull
    private String projectName;

}
