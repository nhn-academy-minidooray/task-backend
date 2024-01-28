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

    @Query(value = "SELECT new com.nhnacademy.minidooray.task.backend.domain.dto.task.TaskInfoResponseDTO(t.id, t.name, t.detail, GROUP_CONCAT(tt.pk.tagId), t.milestone.id) from Task t inner join Project p ON p.id = t.id left join TaskTag tt ON t.id = tt.pk.taskId where p.id = :projectId group by t.id", nativeQuery = true)
    List<TaskInfoResponseDTO> taskList(@Param("projectId") Long projectId);

}