package com.nhnacademy.minidooray.task.backend.domain.requestbody;

import java.util.List;
import lombok.Data;

@Data
public class TaskModifyRequest {
    private Long id;

    private String name;

    private Milestone milestone;

    private List<Tag> tags;

    public static class Milestone {
        Long id;
    }

    public static class Tag {
        Long id;
    }
}
