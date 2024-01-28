package com.nhnacademy.minidooray.task.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.minidooray.task.backend.domain.Status;
import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MilestoneDetailDto;
import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MilestoneDto;
import com.nhnacademy.minidooray.task.backend.domain.dto.project.ProjectDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.milestone.MilestoneRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.project.ProjectRequest;
import com.nhnacademy.minidooray.task.backend.entity.Milestone;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.ProjectMember;
import com.nhnacademy.minidooray.task.backend.exception.ProjectCreationFailedException;
import com.nhnacademy.minidooray.task.backend.exception.ProjectMemberAddFailedException;
import com.nhnacademy.minidooray.task.backend.repository.MilestoneRepository;
import com.nhnacademy.minidooray.task.backend.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray.task.backend.repository.ProjectRepository;
import com.nhnacademy.minidooray.task.backend.service.interfaces.ProjectService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ProjectServiceTest {
    @Autowired
    ProjectService projectService;

    @MockBean
    ProjectRepository projectRepository;

    @MockBean
    MilestoneRepository milestoneRepository;

    @MockBean
    ProjectMemberRepository projectMemberRepository;

    @Test
    void createProjectSuccessTest() {
        ProjectRequest projectRequest = new ProjectRequest("name", "detail", "adminId");
        Project project = Project.builder()
                .id(1L)
                .name("name")
                .adminId("adminId")
                .status(Status.ACTIVATION.getValue())
                .detail("detail")
                .build();

        when(projectRepository.save(any())).thenReturn(project);
        ProjectMember.Pk projectMemberPk = new ProjectMember.Pk("adminId", 1L);
        ProjectMember projectMember = ProjectMember.builder().pk(projectMemberPk).project(project).build();

        when(projectMemberRepository.save(any())).thenReturn(projectMember);

        boolean result = projectService.createProject(projectRequest);

        assertTrue(result);
    }

    @Test
    void createProjectFailureTest() {
        // Given
        ProjectRequest projectRequest = new ProjectRequest("ProjectName", "AdminId", "ProjectDetail");
        Project project =
                Project.builder().id(1L).name("ProjectName").adminId("AdminId").status(Status.ACTIVATION.getValue())
                        .detail("ProjectDetail").build();

        when(projectRepository.save(any())).thenReturn(project);
        when(projectMemberRepository.save(any())).thenThrow(
                new RuntimeException("Simulated error during member registration"));

        assertThrows(ProjectCreationFailedException.class, () -> projectService.createProject(projectRequest));
    }

    @Test
    void createProjectFailTest() {
        ProjectRequest projectRequest = new ProjectRequest("name", "detail", "adminId");
        Project project = Project.builder()
                .id(1L)
                .name("name")
                .adminId("adminId")
                .status(Status.ACTIVATION.getValue())
                .detail("detail")
                .build();

        when(projectRepository.save(any())).thenReturn(project);
        when(projectMemberRepository.save(any())).thenReturn(null);

        assertThrows(ProjectMemberAddFailedException.class, () -> projectService.createProject(projectRequest));
    }


    @Test
    void getProjectListByAccountIdTest() {
        ProjectDto projectDto = new ProjectDto() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "name";
            }

            @Override
            public String getDetail() {
                return "detail";
            }

            @Override
            public String getAdminId() {
                return "adminId";
            }

            @Override
            public String getStatus() {
                return "status";
            }
        };

        String accountId = "testUser";
        List<ProjectDto> expectedProjects = Arrays.asList(projectDto);


        when(projectRepository.getProjectListById(accountId)).thenReturn(expectedProjects);

        List<ProjectDto> actualProjects = projectService.getProjectListByAccountId(accountId);

        assertEquals(expectedProjects.size(), actualProjects.size());
        for (int i = 0; i < expectedProjects.size(); i++) {
            ProjectDto expectedProject = expectedProjects.get(i);
            ProjectDto actualProject = actualProjects.get(i);

            assertEquals(expectedProject.getId(), actualProject.getId());
            assertEquals(expectedProject.getName(), actualProject.getName());
            assertEquals(expectedProject.getDetail(), actualProject.getDetail());
        }
    }

    @Test
    void getProjectDtoByIdTest() {
        ProjectDto projectDto = new ProjectDto() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "name";
            }

            @Override
            public String getDetail() {
                return "detail";
            }

            @Override
            public String getAdminId() {
                return "adminId";
            }

            @Override
            public String getStatus() {
                return "status";
            }
        };

        Long projectId = 1L;
        ProjectDto expectedProject = projectDto;

        when(projectRepository.findProjectById(projectId)).thenReturn(Optional.of(expectedProject));

        Optional<ProjectDto> actualProjectOptional = projectService.getProjectDtoById(projectId);

        assertTrue(actualProjectOptional.isPresent());
        ProjectDto actualProject = actualProjectOptional.get();

        assertEquals(expectedProject.getId(), actualProject.getId());
        assertEquals(expectedProject.getName(), actualProject.getName());
        assertEquals(expectedProject.getDetail(), actualProject.getDetail());
    }

    @Test
    void getProjectDtoByIdNotFoundTest() {
        Long projectId = 1L;
        when(projectRepository.findProjectById(projectId)).thenReturn(Optional.empty());
        Optional<ProjectDto> actualProjectOptional = projectService.getProjectDtoById(projectId);
        assertTrue(actualProjectOptional.isEmpty());
    }

    @Test
    void createMileStoneFailTest() {
        Long projectId = 1L;
        String milestoneName = "Milestone1";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);

        MilestoneRequest milestoneRequest = new MilestoneRequest(projectId, milestoneName, startDate, endDate);
        Project project = new Project();

        when(projectRepository.getProjectById(projectId)).thenReturn(Optional.of(project));

        boolean isProcessed = projectService.createMileStone(milestoneRequest);

        assertFalse(isProcessed);

    }

    @Test
    void createMileStoneTest() {
        Long projectId = 1L;
        String milestoneName = "jaehyon";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);

        MilestoneRequest milestoneRequest = new MilestoneRequest(projectId, milestoneName, startDate, endDate);
        Project project =
                Project.builder()
                        .id(1L)
                        .name("jaehyeon")
                        .status("활성")
                        .detail("detail")
                        .adminId("adminId")
                        .build();

        when(projectRepository.getProjectById(projectId)).thenReturn(Optional.of(project));

        boolean isProcessed = projectService.createMileStone(milestoneRequest);

        assertFalse(isProcessed);

    }

    @Test
    void getMilestoneByProjectTest() {
        Long projectId = 1L;
        List<MilestoneDto> milestoneList = Arrays.asList(
                new MilestoneDto(1L, "Milestone1"),
                new MilestoneDto(2L, "Milestone2")
        );

        when(milestoneRepository.findMileStoneByProjectId(projectId)).thenReturn(milestoneList);

        List<MilestoneDto> result = projectService.getMilestoneByProject(projectId);

        assertEquals(milestoneList.size(), result.size());
        for (int i = 0; i < milestoneList.size(); i++) {
            assertEquals(milestoneList.get(i), result.get(i));
        }
    }

    @Test
    void getMilestoneByProjectNoMilestonesTest() {
        Long projectId = 1L;
        List<MilestoneDto> milestoneList = Collections.emptyList();

        when(milestoneRepository.findMileStoneByProjectId(projectId)).thenReturn(milestoneList);

        List<MilestoneDto> result = projectService.getMilestoneByProject(projectId);

        assertTrue(result.isEmpty());
    }

    @Test
    void getMilestoneByIdTest() {
        Long milestoneId = 1L;
        MilestoneDetailDto milestoneDetail = new MilestoneDetailDto() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "name";
            }

            @Override
            public LocalDate getStartDate() {
                return LocalDate.now();
            }

            @Override
            public LocalDate getEndDate() {
                return LocalDate.now().plusDays(3);
            }

            @Override
            public String getOverOrNot() {
                return "Y";
            }
        };

        when(milestoneRepository.findMilestoneById(milestoneId)).thenReturn(Optional.of(milestoneDetail));

        Optional<MilestoneDetailDto> result = projectService.getMilestoneById(milestoneId);

        assertTrue(result.isPresent());
        assertEquals(milestoneDetail, result.get());
    }

    @Test
    void getMilestoneByIdNotFoundTest() {
        Long milestoneId = 1L;

        when(milestoneRepository.findMilestoneById(milestoneId)).thenReturn(Optional.empty());

        Optional<MilestoneDetailDto> result = projectService.getMilestoneById(milestoneId);

        assertFalse(result.isPresent());
    }

    @Test
    void updateMilestoneTest() {
        Long milestoneId = 1L;
        MilestoneRequest milestoneRequest =
                new MilestoneRequest(1L, "UpdateMilestone", LocalDate.now(), LocalDate.now().plusDays(14));
        Milestone existingMilestone =
                new Milestone(1L, "Milestone", LocalDate.now(), LocalDate.now().plusDays(7), "N", new Project());

        when(milestoneRepository.findById(milestoneId)).thenReturn(Optional.of(existingMilestone));
        when(milestoneRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        boolean result = projectService.updateMilestone(milestoneRequest, milestoneId);

        assertTrue(result);
        verify(milestoneRepository, times(1)).findById(milestoneId);
        verify(milestoneRepository, times(1)).save(any());
    }

    @Test
    void updateMilestoneNotFoundTest() {
        Long milestoneId = 1L;
        MilestoneRequest milestoneRequest =
                new MilestoneRequest(1L, "UpdateMilestone", LocalDate.now(), LocalDate.now().plusDays(14));

        when(milestoneRepository.findById(milestoneId)).thenReturn(Optional.empty());

        boolean result = projectService.updateMilestone(milestoneRequest, milestoneId);

        assertFalse(result);
        verify(milestoneRepository, times(1)).findById(milestoneId);
        verify(milestoneRepository, never()).save(any());
    }


    @Test
    void deleteMilestoneTest() {
        Long milestoneId = 1L;

        when(milestoneRepository.existsById(milestoneId)).thenReturn(true);

        boolean result = projectService.deleteMilestone(milestoneId);

        assertTrue(result);
        verify(milestoneRepository, times(1)).existsById(milestoneId);
        verify(milestoneRepository, times(1)).deleteMilestoneById(milestoneId);
    }

    @Test
    void deleteMilestoneNotFoundTest() {
        Long milestoneId = 1L;

        when(milestoneRepository.existsById(milestoneId)).thenReturn(false);

        boolean result = projectService.deleteMilestone(milestoneId);

        assertFalse(result);
        verify(milestoneRepository, times(1)).existsById(milestoneId);
        verify(milestoneRepository, never()).deleteMilestoneById(any());
    }

    @Test
    void getMilestoneByTaskTest() {
        Long taskId = 1L;
        MilestoneDto milestoneDto = new MilestoneDto(1L, "Milestone 1");

        when(milestoneRepository.findMileStoneByTaskId(taskId)).thenReturn(Optional.of(milestoneDto));

        Optional<MilestoneDto> result = projectService.getMilestoneByTask(taskId);

        assertTrue(result.isPresent());
        assertEquals(milestoneDto, result.get());
        verify(milestoneRepository, times(1)).findMileStoneByTaskId(taskId);
    }

    @Test
    void getMilestoneByTaskNotFoundTest() {
        Long taskId = 1L;

        when(milestoneRepository.findMileStoneByTaskId(taskId)).thenReturn(Optional.empty());

        Optional<MilestoneDto> result = projectService.getMilestoneByTask(taskId);

        assertFalse(result.isPresent());
        verify(milestoneRepository, times(1)).findMileStoneByTaskId(taskId);
    }


}