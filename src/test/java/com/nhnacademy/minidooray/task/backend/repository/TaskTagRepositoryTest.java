package com.nhnacademy.minidooray.task.backend.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.minidooray.task.backend.entity.TaskTag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskTagRepositoryTest {

    @Autowired
    TaskTagRepository taskTagRepository;


    @Test
    void a(){
    }
}