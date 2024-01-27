package com.nhnacademy.minidooray.task.backend.domain.requestbody.task;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
