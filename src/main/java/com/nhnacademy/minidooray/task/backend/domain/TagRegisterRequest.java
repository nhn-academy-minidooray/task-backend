package com.nhnacademy.minidooray.task.backend.domain;

import lombok.Data;

@Data
public class TagRegisterRequest {
    String name;

    Project project;

    public static class Project {
        Long id;
    }

}
