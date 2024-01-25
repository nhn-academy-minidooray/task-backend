//package com.nhnacademy.minidooray.task.backend.repository;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.nhnacademy.minidooray.task.backend.domain.ProjectRegistryRequest;
//import com.nhnacademy.minidooray.task.backend.entity.Project;
//import com.nhnacademy.minidooray.task.backend.entity.ProjectDto;
//import com.nhnacademy.minidooray.task.backend.entity.ProjectMember;
//import com.nhnacademy.minidooray.task.backend.service.ProjectService;
//import java.util.List;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class ProjectRepositoryTest {
//    @Autowired
//    ProjectService projectService;
//
//    @Autowired
//    ProjectMemberRepository projectMemberRepository;
//
//    @Test
//    void test(){
//        ProjectRegistryRequest projectRegistryRequest = new ProjectRegistryRequest("hihi");
//        projectService.createProject(projectRegistryRequest, "jkjk");
//    }
//
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
//
//}