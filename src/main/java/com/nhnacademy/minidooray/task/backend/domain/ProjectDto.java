package com.nhnacademy.minidooray.task.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ProjectDto {
    Long id;
    String name;
    String status;
    String content;
    String adminId;
}