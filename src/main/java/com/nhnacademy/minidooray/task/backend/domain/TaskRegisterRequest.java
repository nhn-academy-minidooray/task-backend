package com.nhnacademy.minidooray.task.backend.domain;

import java.util.List;
import lombok.Data;

@Data
public class TaskRegisterRequest {
    String name;
    Project project;

    Milestone milestone;

    List<Tag> tags;

    public static class Project {
        Long id;
    }

    public static class Milestone {
        Long id;
    }

    public static class Tag {
        Long id;
    }
}
