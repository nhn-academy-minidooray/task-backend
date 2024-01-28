package com.nhnacademy.minidooray.task.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MemberIdOnlyDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.member.ProjectMemberRegisterRequest;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.ProjectMember;
import com.nhnacademy.minidooray.task.backend.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray.task.backend.repository.ProjectRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectMemberServiceImplTest {

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private MemberIdOnlyDTO memberIdOnlyDTO;

    @InjectMocks
    private ProjectMemberServiceImpl projectMemberService;

    @Test
    void testGetProjectMembers() {
        List<MemberIdOnlyDTO> memberIdOnlyDTOList = List.of(memberIdOnlyDTO);

        when(projectMemberRepository.getProjectMembersByPk_ProjectId(1L))
                .thenReturn(memberIdOnlyDTOList);

        List<MemberIdOnlyDTO> result = projectMemberService.getProjectMembers(1L);

        assertEquals(memberIdOnlyDTOList, result);

        verify(projectMemberRepository)
                .getProjectMembersByPk_ProjectId(1L);
    }

    @Test
    @DisplayName("멤버 등록 성공")
    void testRegisterProjectMemberSuccess() {
        ProjectMemberRegisterRequest request = new ProjectMemberRegisterRequest(1L, "tester");

        when(projectRepository.findById(request.getProjectId()))
                .thenReturn(Optional.of(new Project()));

        when(projectMemberRepository.existsById(any(ProjectMember.Pk.class)))
                .thenReturn(false);

        boolean result = projectMemberService.registerProjectMember(request);

        assertTrue(result);

        verify(projectRepository).findById(request.getProjectId());
        verify(projectMemberRepository).existsById(any(ProjectMember.Pk.class));
        verify(projectMemberRepository).save(any(ProjectMember.class));
    }

    @Test
    @DisplayName("Project ID의 프로젝트가 없을 경우, 멤버 등록 실패")
    void testRegisterProjectMemberFailedWhenNotFoundProjectByProjectId() {
        ProjectMemberRegisterRequest request = new ProjectMemberRegisterRequest(1L, "tester");

        when(projectRepository.findById(request.getProjectId()))
                .thenReturn(Optional.empty());

        boolean result = projectMemberService.registerProjectMember(request);

        assertFalse(result);

        verify(projectRepository).findById(request.getProjectId());
        verify(projectMemberRepository, never()).existsById(any(ProjectMember.Pk.class));
        verify(projectMemberRepository, never()).save(any(ProjectMember.class));
    }

    @Test
    @DisplayName("이미 등록된 멤버일 경우, 멤버 등록 실패")
    void testRegisterProjectMemberFailedWhenAlreadyRegisterMember() {
        ProjectMemberRegisterRequest request = new ProjectMemberRegisterRequest(1L, "tester");

        when(projectRepository.findById(request.getProjectId()))
                .thenReturn(Optional.of(new Project()));

        when(projectMemberRepository.existsById(any(ProjectMember.Pk.class)))
                .thenReturn(true);

        boolean result = projectMemberService.registerProjectMember(request);

        assertFalse(result);

        verify(projectRepository).findById(request.getProjectId());
        verify(projectMemberRepository).existsById(any(ProjectMember.Pk.class));
        verify(projectMemberRepository, never()).save(any(ProjectMember.class));
    }

    @Test
    @DisplayName("멤버 삭제 성공")
    void testDeleteProjectMemberSuccess() {
        ProjectMemberRegisterRequest request = new ProjectMemberRegisterRequest(1L, "tester");

        when(projectMemberRepository.findById(
                new ProjectMember.Pk(request.getAccountId(), request.getProjectId())))
                .thenReturn(Optional.of(new ProjectMember()));

        boolean result = projectMemberService.deleteProjectMember(request);

        assertTrue(result);

        verify(projectMemberRepository).deleteById(any(ProjectMember.Pk.class));
    }

    @Test
    @DisplayName("멤버 삭제 실패")
    void testDeleteProjectMemberFailed() {
        ProjectMemberRegisterRequest request = new ProjectMemberRegisterRequest(1L, "tester");

        when(projectMemberRepository.findById(
                new ProjectMember.Pk(request.getAccountId(), request.getProjectId())))
                .thenReturn(Optional.empty());

        boolean result = projectMemberService.deleteProjectMember(request);

        assertFalse(result);

        verify(projectMemberRepository, never()).deleteById(any(ProjectMember.Pk.class));
    }


}