package com.nhnacademy.minidooray.task.backend.repository;

import com.nhnacademy.minidooray.task.backend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
