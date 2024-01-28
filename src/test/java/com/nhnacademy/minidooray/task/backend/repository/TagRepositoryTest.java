package com.nhnacademy.minidooray.task.backend.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.minidooray.task.backend.domain.Status;
import com.nhnacademy.minidooray.task.backend.domain.dto.tag.TagDTO;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.Tag;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TagRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    TagRepository tagRepository;

    @Test
    void testFindAllByProject_id() {
        Project project = Project.builder()
                .name("testProject")
                .status(Status.ACTIVATION.getValue())
                .detail("detail")
                .adminId("tester")
                .build();

        Project attachedProject = testEntityManager.merge(project);

        Tag tag = new Tag();
        tag.setName("testTag");
        tag.setProject(attachedProject);

        Tag attatchedTag = testEntityManager.merge(tag);

        List<TagDTO> result = tagRepository.findAllByProject_Id(attachedProject.getId());

        assertEquals(1, result.size());
        assertEquals(attatchedTag.getId(), result.get(0).getId());
        assertEquals(attatchedTag.getName(), result.get(0).getName());
    }

    @Test
    void testFindByName() {
        Project project = Project.builder()
                .name("testProject")
                .status(Status.ACTIVATION.getValue())
                .detail("detail")
                .adminId("tester")
                .build();

        Project attachedProject = testEntityManager.merge(project);

        Tag tag = new Tag();
        tag.setName("testTag");
        tag.setProject(attachedProject);

        Tag attatchedTag = testEntityManager.merge(tag);

        Tag result = tagRepository.findByName("testTag").orElse(null);

        if(Objects.nonNull(result)){
            assertEquals(attatchedTag.getId(), result.getId());
            assertEquals("testTag", result.getName());
        }

    }

}