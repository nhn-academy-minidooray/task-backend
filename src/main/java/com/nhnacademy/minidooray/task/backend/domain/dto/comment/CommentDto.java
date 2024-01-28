package com.nhnacademy.minidooray.task.backend.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class CommentDto {
    private Long id;

    private String owner;

    private String content;
}
