package com.nhnacademy.minidooray.task.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.minidooray.task.backend.domain.dto.tag.TagDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.tag.TagNameOnlyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.tag.TagRegisterRequest;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.Tag;
import com.nhnacademy.minidooray.task.backend.repository.ProjectRepository;
import com.nhnacademy.minidooray.task.backend.repository.TagRepository;
import com.nhnacademy.minidooray.task.backend.repository.TaskTagRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TaskTagRepository taskTagRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TagDTO tagDTO;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void testFindAllByProjectId() {
        List<TagDTO> tagDTOList = List.of(tagDTO);

        when(tagRepository.findAllByProject_Id(1L))
                .thenReturn(tagDTOList);

        List<TagDTO> result = tagService.findAllByProjectId(1L);

        assertEquals(tagDTOList, result);

        verify(tagRepository).findAllByProject_Id(1L);
    }

    @Test
    void testFindAllByTaskId() {
        List<TagDTO> tagDTOList = List.of(tagDTO);

        when(taskTagRepository.findAllByTaskId(1L))
                .thenReturn(tagDTOList);

        List<TagDTO> result = tagService.findAllByTaskId(1L);

        assertEquals(tagDTOList, result);

        verify(taskTagRepository).findAllByTaskId(1L);
    }

    @Test
    @DisplayName("태그 생성 성공")
    void testCreateTagSuccess() {
        TagRegisterRequest request = new TagRegisterRequest(1L, "test");

        when(projectRepository.findById(request.getProjectId()))
                .thenReturn(Optional.of(new Project()));

        when(tagRepository.findByName(request.getName()))
                .thenReturn(Optional.empty());

        boolean result = tagService.createTag(request);

        assertTrue(result);

        verify(projectRepository).findById(request.getProjectId());
        verify(tagRepository).findByName(request.getName());
        verify(tagRepository).save(any(Tag.class));
    }

    @Test
    @DisplayName("프로젝트가 존재하지 않을 경우, 태그 생성 실패")
    void testCreateTagFailedWhenNotFoundProject() {
        TagRegisterRequest request = new TagRegisterRequest(1L, "test");

        when(projectRepository.findById(request.getProjectId()))
                .thenReturn(Optional.empty());

        when(tagRepository.findByName(request.getName()))
                .thenReturn(Optional.empty());

        boolean result = tagService.createTag(request);

        assertFalse(result);

        verify(projectRepository).findById(request.getProjectId());
        verify(tagRepository).findByName(request.getName());
        verify(tagRepository, never()).save(any(Tag.class));
    }

    @Test
    @DisplayName("이미 존재하는 이름의 태그가 있는 경우, 태그 생성 실패")
    void testCreateTagFailedWhenAlreadyExistsTagName() {
        TagRegisterRequest request = new TagRegisterRequest(1L, "test");

        when(projectRepository.findById(request.getProjectId()))
                .thenReturn(Optional.of(new Project()));

        when(tagRepository.findByName(request.getName()))
                .thenReturn(Optional.of(new Tag()));

        boolean result = tagService.createTag(request);

        assertFalse(result);

        verify(projectRepository).findById(request.getProjectId());
        verify(tagRepository).findByName(request.getName());
        verify(tagRepository, never()).save(any(Tag.class));
    }

    @Test
    @DisplayName("태그 수정 성공")
    void testModifyTagSuccess() {
        TagNameOnlyRequest request = new TagNameOnlyRequest("modified");
        Tag tag = new Tag(1L, "testTag", new Project());

        when(tagRepository.findById(1L))
                .thenReturn(Optional.of(tag));

        when(tagRepository.findByName(request.getName()))
                .thenReturn(Optional.empty());

        boolean result = tagService.modifyTag(1L, request);

        assertTrue(result);
        assertEquals(request.getName(), tag.getName());

        verify(tagRepository).findById(1L);
        verify(tagRepository).findByName(request.getName());
        verify(tagRepository).save(any(Tag.class));
    }

    @Test
    @DisplayName("이미 존재하는 이름의 태그가 있는 경우, 태그 수정 실패")
    void testModifyTagFailedWhenAlreadyExitsTagName() {
        TagNameOnlyRequest request = new TagNameOnlyRequest("modified");
        Tag tag = new Tag(1L, "testTag", new Project());

        when(tagRepository.findById(1L))
                .thenReturn(Optional.of(tag));

        when(tagRepository.findByName(request.getName()))
                .thenReturn(Optional.of(new Tag()));

        boolean result = tagService.modifyTag(1L, request);

        assertFalse(result);
        assertNotEquals(request.getName(), tag.getName());

        verify(tagRepository).findById(1L);
        verify(tagRepository).findByName(request.getName());
        verify(tagRepository, never()).save(any(Tag.class));
    }

    @Test
    @DisplayName("태그 ID에 맞는 태그가 없을 경우, 태그 수정 실패")
    void testModifyTagFailedWhenNotFoundTagByTagId() {
        TagNameOnlyRequest request = new TagNameOnlyRequest("modified");

        when(tagRepository.findById(1L))
                .thenReturn(Optional.empty());

        when(tagRepository.findByName(request.getName()))
                .thenReturn(Optional.empty());

        boolean result = tagService.modifyTag(1L, request);

        assertFalse(result);

        verify(tagRepository).findById(1L);
        verify(tagRepository).findByName(request.getName());
        verify(tagRepository, never()).save(any(Tag.class));
    }

    @Test
    @DisplayName("태그 삭제 성공")
    void testDeleteTagSuccess() {
        when(tagRepository.existsById(1L))
                .thenReturn(true);

        boolean result = tagService.deleteTag(1L);

        assertTrue(result);

        verify(tagRepository).existsById(1L);
        verify(tagRepository).deleteById(1L);
    }

    @Test
    @DisplayName("태그 삭제 실패")
    void testDeleteTagFailed() {
        when(tagRepository.existsById(1L))
                .thenReturn(false);

        boolean result = tagService.deleteTag(1L);

        assertFalse(result);

        verify(tagRepository).existsById(1L);
        verify(tagRepository, never()).deleteById(anyLong());
    }

}