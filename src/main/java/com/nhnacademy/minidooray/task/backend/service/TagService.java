package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.dto.TagDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.TagNameOnlyRequest;
import java.util.List;

public interface TagService {
    List<TagDTO> findAllByProjectId(Long projectId);

    boolean createTag(Long projectId, TagNameOnlyRequest request);

    boolean modifyTag(Long id, TagNameOnlyRequest request);

    boolean deleteTag(Long id);
}
