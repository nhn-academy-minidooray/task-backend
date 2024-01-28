package com.nhnacademy.minidooray.task.backend.domain.dto.task;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    List<Long> tagList;
    Long milestoneId;

    public TaskInfoResponseDTO(Long id, String name, String detail, String tagList, Long milestoneId) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.tagList = convertToTagIdsList(tagList);
        this.milestoneId = milestoneId;
    }

    private List<Long> convertToTagIdsList(String tagList) {
        List<Long> tagIdList = new ArrayList<>();
        if (tagList != null && !tagList.isEmpty()) {
            String[] tagIdArray = tagList.split(",");
            for (String tagId : tagIdArray) {
                tagIdList.add(Long.parseLong(tagId));
            }
        }
        return tagIdList;
    }

}
