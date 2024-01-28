package com.nhnacademy.minidooray.task.backend.domain.requestbody.comment;

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
    private String owner;

    @NotNull
    private String content;


}
