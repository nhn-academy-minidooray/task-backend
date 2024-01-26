package com.nhnacademy.minidooray.task.backend.domain;

import lombok.Data;

@Data
public class CommentModifyRequest {
    Long id;

    String content;
}
