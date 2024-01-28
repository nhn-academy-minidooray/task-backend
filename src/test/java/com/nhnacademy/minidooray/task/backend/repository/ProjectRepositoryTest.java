package com.nhnacademy.minidooray.task.backend.repository;


import com.nhnacademy.minidooray.task.backend.domain.ProjectDto;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import org.junit.jupiter.api.Assertions;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.project.ProjectRequest;

import com.nhnacademy.minidooray.task.backend.service.interfaces.ProjectService;
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

//    @Test
//    void test(){
//        ProjectRequest projectRegistryRequest = new ProjectRequest("name", "id");
//        projectService.createProject(projectRegistryRequest);
//    }

//    @Test
//    void test1(){
//
//        List<ProjectDto> jkjk = projectService.getProjectListByAccountId("jkjk");
//
//        System.out.println("00-0--00----0-0-0-0--0-0-");
//        System.out.println("00-0--00----0-0-0-0--0-0-");
//        System.out.println("00-0--00----0-0-0-0--0-0-");
//        System.out.println("00-0--00----0-0-0-0--0-0-");
//        System.out.println("00-0--00----0-0-0-0--0-0-");
//        System.out.println("00-0--00----0-0-0-0--0-0-");
//        System.out.println("00-0--00----0-0-0-0--0-0-");
//        jkjk.stream().forEach(System.out::println);
//
//
//    }
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

//    @Test
//    void as(){
//        projectRepository.getProjectListById("jkjk").stream().forEach(System.out::println);
//    }

}