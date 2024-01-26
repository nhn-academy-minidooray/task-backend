package com.nhnacademy.minidooray.task.backend.domain.requestbody;

import lombok.Data;

@Data
public class CommentRegisterRequest {
    String content;

    Task task;

    @Data
    public static class Task {
        Long id;
    }
}