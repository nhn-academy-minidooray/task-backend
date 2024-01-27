package com.nhnacademy.minidooray.task.backend.repository;

import com.nhnacademy.minidooray.task.backend.domain.dto.TagDTO;
import com.nhnacademy.minidooray.task.backend.entity.TaskTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskTagRepository extends JpaRepository<TaskTag, TaskTag.Pk> {
    @Query("SELECT " +
            "tt.pk.tagId as id, tt.tag.name as name " +
            "FROM TaskTag tt " +
            "WHERE tt.pk.taskId = ?1")
    List<TagDTO> findAllByTaskId(Long taskId);
}
