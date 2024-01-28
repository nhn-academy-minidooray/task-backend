package com.nhnacademy.minidooray.task.backend.domain.requestbody.comment;

import lombok.Data;

@Data
public class CommentModifyRequest {
    Long id;

    String content;
}
