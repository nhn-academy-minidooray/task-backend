package com.nhnacademy.minidooray.task.backend.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.minidooray.task.backend.domain.Status;
import com.nhnacademy.minidooray.task.backend.domain.dto.comment.CommentDto;
import com.nhnacademy.minidooray.task.backend.entity.Comment;
import com.nhnacademy.minidooray.task.backend.entity.Milestone;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.Task;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CommentRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    CommentRepository commentRepository;

    @Test
    void testCommentListByTaskId() {
        Project project = Project.builder()
                .name("testProject")
                .status(Status.ACTIVATION.getValue())
                .detail("detail")
                .adminId("tester")
                .build();

        Project attachedProject = testEntityManager.merge(project);

        Milestone milestone = Milestone.builder()
                .name("testMilestone")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .overOrNot("N")
                .project(attachedProject)
                .build();

        Milestone attachedMilestone = testEntityManager.merge(milestone);

        Task task = Task.builder()
                .name("testTask")
                .detail("test")
                .project(attachedProject)
                .milestone(attachedMilestone)
                .build();

        Task attachedTask = testEntityManager.merge(task);

        Comment comment = Comment.builder()
                .owner("tester")
                .content("testComment")
                .task(attachedTask)
                .build();

        Comment attachedComment = testEntityManager.merge(comment);

        List<CommentDto> result = commentRepository.findAllByTask_Id(attachedTask.getId());

        assertEquals(1, result.size());
        assertEquals(comment.getOwner(), result.get(0).getOwner());
        assertEquals(comment.getContent(), result.get(0).getContent());
        assertEquals(attachedComment.getId(), result.get(0).getId());
    }
}