package com.nhnacademy.minidooray.task.backend.repository;


import com.nhnacademy.minidooray.task.backend.domain.requestbody.project.ProjectRequest;

import com.nhnacademy.minidooray.task.backend.service.interfaces.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectRepositoryTest {
    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectRepository projectRepository;


    @Test
    void test(){
        ProjectRequest projectRegistryRequest = new ProjectRequest("name", "id");
        projectService.createProject(projectRegistryRequest);
    }

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
    void as(){
        projectRepository.getProjectListById("jkjk").stream().forEach(System.out::println);
    }

}