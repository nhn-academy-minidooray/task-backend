package com.nhnacademy.minidooray.task.backend.domain;

import java.util.List;
import lombok.Data;

@Data
public class TaskModifyRequest {
    Long id;

    String name;

    Milestone milestone;

    List<Tag> tags;
    public static class Milestone {
        Long id;
    }

    public static class Tag {
        Long id;
    }
}
