package com.nhnacademy.minidooray.task.backend.repository;

import com.nhnacademy.minidooray.task.backend.domain.dto.comment.CommentDto;
import com.nhnacademy.minidooray.task.backend.entity.Comment;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.Task;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TestEntityManager testEntityManager;


    @Test
    void commentListByTaskIdTest() {
        Project saveProject = testEntityManager.persist(Project.builder()
                .name("dooray")
                .status("활성")
                .adminId("jkjk")
                .build());

        Task task = testEntityManager.persist(Task.builder()
                .name("hihi")
                .detail("test")
                .project(saveProject)
                .milestone(null)
                .build());

        Comment comment = testEntityManager.persist(Comment.builder()
                .content("content")
                .task(task)
                .build());

        List<CommentDto> commentDtoList = commentRepository.commentListByTaskId(task.getId());

        Assertions.assertFalse(commentDtoList.isEmpty());
        Assertions.assertNotNull(commentDtoList);
        Assertions.assertEquals(comment.getId(), commentDtoList.get(0).getGetId());
        Assertions.assertEquals(comment.getContent(), commentDtoList.get(0).getGetContent());
    }

}