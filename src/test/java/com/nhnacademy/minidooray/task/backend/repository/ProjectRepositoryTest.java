package com.nhnacademy.minidooray.task.backend.repository;


import com.nhnacademy.minidooray.task.backend.domain.ProjectDto;
import com.nhnacademy.minidooray.task.backend.entity.Project;
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
    void d() {
        Project project = testProjectBuilder();


    }

    @Test
    void findProjectByIdTest() {
        Project saveProject = testEntityManager.persist(testProjectBuilder());

        ProjectDto findProject = projectRepository.findProjectById(saveProject.getId()).orElse(null);

        Assertions.assertNotNull(findProject);
        Assertions.assertEquals(findProject.getId(), saveProject.getId());
        Assertions.assertEquals(findProject.getName(), saveProject.getName());
    }

    @Test
    void getProjectByIdTest(){
        Project saveProject = testEntityManager.persist(testProjectBuilder());

        Project findProject = projectRepository.getProjectById(saveProject.getId());

        Assertions.assertNotNull(findProject);
        Assertions.assertEquals(findProject.getId(), saveProject.getId());
        Assertions.assertEquals(findProject.getName(), saveProject.getName());
        Assertions.assertEquals(findProject.getStatus(), saveProject.getStatus());
        Assertions.assertEquals(findProject.getAdminId(), saveProject.getAdminId());
    }

    private static Project testProjectBuilder() {
        return Project.builder()
                .name("dooray")
                .status("활성")
                .adminId("jkjk")
                .build();
    }


}