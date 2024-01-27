package com.nhnacademy.minidooray.task.backend.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    private Project project;
    private String name;
    private Milestone milestone;
    private List<Tag> tags;


    @Data
    public static class Project {
        Long id;
    }

    @Data
    public static class Milestone {
        Long id;
    }

    @Data
    public static class Tag {
        Long id;
    }
}
