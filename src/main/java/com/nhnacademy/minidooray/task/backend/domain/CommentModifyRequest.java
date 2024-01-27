package com.nhnacademy.minidooray.task.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentModifyRequest {
    private Long id;

    private String content;
}
