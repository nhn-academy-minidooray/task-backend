package com.nhnacademy.minidooray.task.backend.domain.requestbody.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentModifyRequest {
    String owner;

    String content;
}
