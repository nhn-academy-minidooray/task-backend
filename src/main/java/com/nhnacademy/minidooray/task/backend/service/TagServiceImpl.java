package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.dto.tag.TagDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.tag.TagNameOnlyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.tag.TagRegisterRequest;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.Tag;
import com.nhnacademy.minidooray.task.backend.repository.ProjectRepository;
import com.nhnacademy.minidooray.task.backend.repository.TagRepository;
import com.nhnacademy.minidooray.task.backend.repository.TaskTagRepository;
import com.nhnacademy.minidooray.task.backend.service.interfaces.TagService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    private final TaskTagRepository taskTagRepository;

    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    @Override
    public List<TagDTO> findAllByProjectId(Long projectId) {
        return tagRepository.findAllByProject_Id(projectId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TagDTO> findAllByTaskId(Long taskId) {
        return taskTagRepository.findAllByTaskId(taskId);
    }

    @Transactional
    @Override
    public boolean createTag(TagRegisterRequest request) {
        Optional<Project> project = projectRepository.findById(request.getProjectId());

        if(tagRepository.findByName(request.getName()).isPresent()){
            log.error("createTag() : Already exist tag name {}", request.getName());

            return false;
        }

        if(project.isPresent()) {
            Tag tag = new Tag(request.getName(), project.get());
            tagRepository.save(tag);

            return true;
        }

        log.error("createTag() : Not found project by projectId {}", request.getProjectId());

        return false;
    }

    @Transactional
    @Override
    public boolean modifyTag(Long id, TagNameOnlyRequest request) {
        Optional<Tag> tag = tagRepository.findById(id);

        if(tagRepository.findByName(request.getName()).isPresent()){
            log.error("createTag() : Already exist tag name {}", request.getName());

            return false;
        }

        if(tag.isPresent()){
            Tag modifiedTag = tag.get();
            modifiedTag.setName(request.getName());
            tagRepository.save(modifiedTag);

            return true;
        }

        log.error("modifyTag() : Not found tag by tagId {}", id);

        return false;
    }

    @Transactional
    @Override
    public boolean deleteTag(Long id) {
        if(tagRepository.existsById(id)){
            tagRepository.deleteById(id);

            return true;
        }

        log.error("deleteTag() : Not found tag by tagId {}", id);

        return false;
    }
}
