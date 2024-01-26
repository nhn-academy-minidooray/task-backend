package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.dto.TagDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.TagNameOnlyRequest;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.Tag;
import com.nhnacademy.minidooray.task.backend.repository.ProjectRepository;
import com.nhnacademy.minidooray.task.backend.repository.TagRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepository;

    private final ProjectRepository projectRepository;

    @Override
    public List<TagDTO> findAllByProjectId(Long projectId) {
        return tagRepository.findAllByProject_Id(projectId);
    }

    @Transactional
    @Override
    public boolean createTag(Long projectId, TagNameOnlyRequest request) {
        Optional<Project> project = projectRepository.findById(projectId);

        if(project.isPresent()) {
            Tag tag = new Tag(request.getName(), project.get());
            tagRepository.save(tag);

            return true;
        }

        log.error("createTag() : Not found project by {}", projectId);

        return false;
    }

    @Transactional
    @Override
    public boolean modifyTag(Long id, TagNameOnlyRequest request) {
        Optional<Tag> tag = tagRepository.findById(id);

        if(tag.isPresent()){
            Tag modifiedTag = tag.get();
            modifiedTag.setName(request.getName());
            tagRepository.save(modifiedTag);

            return true;
        }

        log.error("modifyTag() : Not found tag by {}", id);

        return false;
    }

    @Override
    public boolean deleteTag(Long id) {
        if(tagRepository.existsById(id)){
            tagRepository.deleteById(id);

            return true;
        }

        return false;
    }
}
