package com.nhnacademy.minidooray.task.backend.domain.requestbody;

import java.util.List;
import lombok.Data;

@Data
public class TaskRegisterRequest {
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
