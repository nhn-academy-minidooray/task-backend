package com.nhnacademy.minidooray.task.backend.repository;

import com.nhnacademy.minidooray.task.backend.domain.MilestoneDetailDto;
import com.nhnacademy.minidooray.task.backend.domain.MilestoneDto;
import com.nhnacademy.minidooray.task.backend.entity.Milestone;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

    Optional<MilestoneDetailDto> findMilestoneById(Long id);

    Optional<Milestone> findById(Long mileId);

    @Query("SELECT new com.nhnacademy.minidooray.task.backend.domain.MilestoneDto(m.id, m.name) from Milestone m INNER JOIN Project p ON p.id = m.project.id WHERE p.id = :id")
    List<MilestoneDto> findMileStoneByProjectId(@Param("id") Long id);

    @Query("SELECT new com.nhnacademy.minidooray.task.backend.domain.MilestoneDto(m.id, m.name) from Milestone m INNER JOIN Task t ON m.id = t.milestone.id WHERE t.id  = :taskId")
    Optional<MilestoneDto> findMileStoneByTaskId(@Param("taskId") Long id);

    List<Milestone> findAll();

    void deleteMilestoneById(Long id);
}
