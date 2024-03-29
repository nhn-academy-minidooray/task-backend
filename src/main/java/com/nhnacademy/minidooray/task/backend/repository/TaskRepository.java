package com.nhnacademy.minidooray.task.backend.repository;

import com.nhnacademy.minidooray.task.backend.domain.dto.task.TaskDto;
import com.nhnacademy.minidooray.task.backend.entity.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<TaskDto> findTaskById(Long taskId);

    void deleteTaskById(Long taskId);

    Optional<Task> getTaskById(Long taskId);

    @Query(value =
            "SELECT t.task_id, t.task_name, t.task_detail, GROUP_CONCAT(tt.tag_id), GROUP_CONCAT(tag.tag_name), t.milestone_id, m.milestone_name " +
                    "FROM Task t " +
                    "INNER JOIN Project p ON p.project_id = t.project_id " +
                    "LEFT JOIN `Task-Tag` tt ON t.task_id = tt.task_id " +
                    "LEFT JOIN Milestone m ON t.milestone_id = m.milestone_id " +
                    "LEFT JOIN Tag tag ON tt.tag_id = tag.tag_id " +
                    "WHERE p.project_id = :projectId " +
                    "GROUP BY t.task_id", nativeQuery = true)
    List<List<Object>> nativeTaskList(@Param("projectId") Long projectId);
}