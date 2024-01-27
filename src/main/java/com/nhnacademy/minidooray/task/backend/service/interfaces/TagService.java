package com.nhnacademy.minidooray.task.backend.service.interfaces;

import com.nhnacademy.minidooray.task.backend.domain.dto.tag.TagDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.tag.TagNameOnlyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.tag.TagRegisterRequest;
import java.util.List;

public interface TagService {
    List<TagDTO> findAllByProjectId(Long projectId);

    List<TagDTO> findAllByTaskId(Long taskId);

    boolean createTag(TagRegisterRequest request);

    boolean modifyTag(Long id, TagNameOnlyRequest request);

    boolean deleteTag(Long id);
}
