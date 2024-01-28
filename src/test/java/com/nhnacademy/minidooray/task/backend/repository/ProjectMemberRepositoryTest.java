package com.nhnacademy.minidooray.task.backend.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.minidooray.task.backend.domain.Status;
import com.nhnacademy.minidooray.task.backend.domain.dto.milestone.MemberIdOnlyDTO;
import com.nhnacademy.minidooray.task.backend.entity.Project;
import com.nhnacademy.minidooray.task.backend.entity.ProjectMember;
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
class ProjectMemberRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Test
    void testGetProjectMembersByPk_ProjectId() {
        Project project = Project.builder()
                .name("testProject")
                .status(Status.ACTIVATION.getValue())
                .detail("detail")
                .adminId("tester")
                .build();

        Project attachedProject = testEntityManager.merge(project);

        ProjectMember.Pk pk = new ProjectMember.Pk("tester", attachedProject.getId());

        ProjectMember member = ProjectMember.builder()
                .pk(pk)
                .project(attachedProject)
                .build();

        testEntityManager.merge(member);

        List<MemberIdOnlyDTO> result = projectMemberRepository
                .getProjectMembersByPk_ProjectId(attachedProject.getId());

        assertEquals(1, result.size());
        assertEquals(pk.getAccountId(), result.get(0).getAccountId());
    }

}