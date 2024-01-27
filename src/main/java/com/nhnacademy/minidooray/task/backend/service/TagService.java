package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.dto.TagDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.ProjectIdOnlyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.TagNameOnlyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.TagRegisterRequest;
import java.util.List;

public interface TagService {
    List<TagDTO> findAllByProjectId(Long projectId);

    List<TagDTO> findAllByTaskId(Long taskId);

    boolean createTag(TagRegisterRequest request);

    boolean modifyTag(Long id, TagNameOnlyRequest request);

    boolean deleteTag(Long id);
}
