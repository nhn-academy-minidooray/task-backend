package com.nhnacademy.minidooray.task.backend.domain.requestbody.comment;

import lombok.Data;

@Data
public class CommentModifyRequest {
    String owner;

    String content;
}
