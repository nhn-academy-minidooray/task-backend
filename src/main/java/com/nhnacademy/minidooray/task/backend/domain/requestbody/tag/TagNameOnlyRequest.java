package com.nhnacademy.minidooray.task.backend.domain.requestbody.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagNameOnlyRequest {
    String name;
}
