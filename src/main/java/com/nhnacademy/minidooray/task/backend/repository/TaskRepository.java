package com.nhnacademy.minidooray.task.backend.repository;

import com.nhnacademy.minidooray.task.backend.domain.dto.task.TaskDto;
import com.nhnacademy.minidooray.task.backend.domain.dto.task.TaskInfoResponseDTO;
import com.nhnacademy.minidooray.task.backend.entity.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT " +
            "new com.nhnacademy.minidooray.task.backend.domain.dto.task.TaskDto(t.id, t.name, t.detail) " +
            "from Task t " +
            "inner join Project p " +
            "ON t.project.id = p.id " +
            "WHERE p.id = :projectId")
    List<TaskDto> taskListByProjectId(@Param("projectId") Long projectId);

    Optional<TaskDto> findTaskById(Long taskId);

    void deleteTaskById(Long taskId);

    Task getTaskById(Long taskId);


    @Query(value = "SELECT t.task_id, t.task_name, t.task_detail, GROUP_CONCAT(tt.tag_id), t.milestone_id " +
            "FROM Task t " +
            "INNER JOIN Project p ON p.project_id = t.project_id " +
            "LEFT JOIN `Task-Tag` tt ON t.task_id = tt.task_id " +
            "WHERE p.project_id = :projectId " +
            "GROUP BY t.task_id", nativeQuery = true)
    List<List<Object>> nativeTaskList(@Param("projectId") Long projectId);

}