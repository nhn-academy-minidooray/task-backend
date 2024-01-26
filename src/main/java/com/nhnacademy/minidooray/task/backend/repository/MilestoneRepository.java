package com.nhnacademy.minidooray.task.backend.repository;

import com.nhnacademy.minidooray.task.backend.domain.MilestoneDetailDto;
import com.nhnacademy.minidooray.task.backend.entity.Milestone;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

    Optional<MilestoneDetailDto> findMilestoneById(Long id);

    @Query("SELECT m.id, m.name from Milestone m INNER JOIN Project p ON p.id = m.project.id WHERE p.id = :id")
    List<List<Object>> findMileStoneByProjectId(@Param("id") Long id);

    @Query("SELECT m.id, m.name from Milestone m  INNER JOIN Project p ON p.id = m.project.id WHERE p.id = :projectId AND m.id= :milestoneId")
    MilestoneDetailDto findMilestoneByProjectIdAndMilestoneId(@Param("projectId") Long projectId,
                                                              @Param("milestoneId") Long milestoneId);

    List<Milestone> findAll();

    void deleteMilestoneById(Long id);
}
