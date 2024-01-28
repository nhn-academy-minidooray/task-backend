package com.nhnacademy.minidooray.task.backend.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nhnacademy.minidooray.task.backend.domain.dto.project.ProjectDto;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.ProjectMember;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void findProjectByIdTest() {
        Project saveProject = testEntityManager.persist(Project.builder()
                .name("dooray")
                .status("활성")
                .adminId("jkjk")
                .build());

        ProjectDto findProject = projectRepository.findProjectById(saveProject.getId()).orElse(null);

        Assertions.assertNotNull(findProject);
        assertEquals(findProject.getId(), saveProject.getId());
        assertEquals(findProject.getName(), saveProject.getName());
        assertEquals(findProject.getDetail(), saveProject.getDetail());
        assertEquals(findProject.getStatus(), saveProject.getStatus());
    }

    @Test
    void getProjectListByIdTest(){
        String accountId = "admin1";
        Project saveProject1 = testEntityManager.persist(Project.builder()
                .name("dooray")
                .status("활성")
                .adminId(accountId)
                .build());

        ProjectMember.Pk pk = new ProjectMember.Pk().builder().projectId(saveProject1.getId()).accountId(accountId).build();
        ProjectMember projectMember = ProjectMember.builder().project(saveProject1).pk(pk).build();
        testEntityManager.persist(projectMember);
        List<ProjectDto> projectList = projectRepository.getProjectListById("admin1");

        Assertions.assertNotNull(projectList);
        assertEquals(1, projectList.size());
        assertEquals(saveProject1.getId(), projectList.get(0).getId());
        assertEquals(saveProject1.getName(), projectList.get(0).getName());
        assertEquals(saveProject1.getDetail(), projectList.get(0).getDetail());
        assertEquals(saveProject1.getStatus(), projectList.get(0).getStatus());
    }

    @Test
    void getProjectByIdTest() {
        Project saveProject = testEntityManager.persist(Project.builder()
                .name("dooray")
                .status("활성")
                .adminId("jkjk")
                .build());

        Project findProject = projectRepository.getProjectById(saveProject.getId()).orElse(null);

        Assertions.assertNotNull(findProject);
        assertEquals(findProject.getId(), saveProject.getId());
        assertEquals(findProject.getName(), saveProject.getName());
        assertEquals(findProject.getDetail(), saveProject.getDetail());
        assertEquals(findProject.getStatus(), saveProject.getStatus());
    }


//    @Test
//    void getProjectByIdTest(){
//        Project saveProject = testEntityManager.persist(testProjectBuilder());
//
//        Optional<Project> findProject = projectRepository.getProjectById(saveProject.getId());
//
//        Assertions.assertNotNull(findProject);
//        assertEquals(findProject.getId(), saveProject.getId());
//        assertEquals(findProject.getName(), saveProject.getName());
//        assertEquals(findProject.getStatus(), saveProject.getStatus());
//        assertEquals(findProject.getAdminId(), saveProject.getAdminId());
//    }

    private static Project testProjectBuilder() {
        return Project.builder()
                .name("dooray")
                .status("활성")
                .adminId("jkjk")
                .build();
    }


}