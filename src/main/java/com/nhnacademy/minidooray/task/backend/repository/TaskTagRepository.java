package com.nhnacademy.minidooray.task.backend.repository;

import com.nhnacademy.minidooray.task.backend.domain.dto.tag.TagDTO;
import com.nhnacademy.minidooray.task.backend.entity.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface TaskTagRepository extends JpaRepository<TaskTag, TaskTag.Pk> {
    @Query("SELECT " +
            "tt.pk.tagId as id, t.name as name " +
            "FROM TaskTag tt " +
            "INNER JOIN Tag t " +
            "ON tt.pk.tagId = t.id " +
            "WHERE tt.pk.taskId = ?1")
    List<TagDTO> findAllByTaskId(Long taskId);
}
