package com.nhnacademy.minidooray.task.backend.domain.dto.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TaskInfoResponseDTO {
    Long id;
    String name;
    String detail;
    List<Long> tagIdList;
    List<String> tagNameList;

    Long milestoneId;
    String milestoneName;

    public TaskInfoResponseDTO(Long id, String name, String detail, String tagIdList, String tagNameList, Long milestoneId, String milestoneName) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.tagIdList = Arrays.stream(tagIdList.split(",")).map(Long::parseLong).collect(Collectors.toList());
        this.tagNameList = Arrays.stream(tagNameList.split(",")).collect(Collectors.toList());
        this.milestoneId = milestoneId;
        this.milestoneName = milestoneName;
    }

}
