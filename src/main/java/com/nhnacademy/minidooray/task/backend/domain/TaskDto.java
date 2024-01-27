package com.nhnacademy.minidooray.task.backend.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TaskDto {
    private Long id;

    private String name;
}
