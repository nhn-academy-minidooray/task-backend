package com.nhnacademy.minidooray.task.backend.domain;

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