package com.nhnacademy.minidooray.task.backend.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Milestone")
public class Milestone {
    @Id
    @Column(name = "milestone_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long milestoneId;

    @NotNull
    @Column(name = "milestone_name")
    private String milestoneName;

    @NotNull
    @Column(name = "milestone_start_date")
    private LocalDate milestoneStartDate;

    @NotNull
    @Column(name = "milestone_end_date")
    private LocalDate milestoneEndDate;

    @NotNull
    @Column(name = "milestone_over_or_not")
    private String milestoneOverOrNot;
}
