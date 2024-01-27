package com.nhnacademy.minidooray.task.backend.domain;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    @NotNull
    private Long taskId;
    @NotNull
    private String content;


}
