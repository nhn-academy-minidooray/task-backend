package com.nhnacademy.minidooray.task.backend.domain.requestbody;

import lombok.Data;

@Data
public class CommentModifyRequest {
    Long id;

    String content;
}
