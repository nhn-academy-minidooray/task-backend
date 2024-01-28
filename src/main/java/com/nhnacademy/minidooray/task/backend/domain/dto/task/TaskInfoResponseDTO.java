package com.nhnacademy.minidooray.task.backend.domain.dto.task;


import java.util.Arrays;
import java.util.Collections;
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
    private Long id;
    private String name;
    private String detail;
    private List<Long> tagIdList;
    private List<String> tagNameList;

    private Long milestoneId;
    private String milestoneName;

    public TaskInfoResponseDTO(Long id, String name, String detail, String tagIdList, String tagNameList,
                               Long milestoneId, String milestoneName) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.tagIdList = !tagIdList.isEmpty() ?
                Arrays.stream(tagIdList.split(",")).map(Long::parseLong).collect(Collectors.toList()) :
                Collections.emptyList();
        this.tagNameList = !tagNameList.isEmpty() ? Arrays.stream(tagNameList.split(",")).collect(Collectors.toList()) :
                Collections.emptyList();
        this.milestoneId = milestoneId;
        this.milestoneName = milestoneName;
    }

}
