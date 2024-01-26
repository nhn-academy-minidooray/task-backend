package com.nhnacademy.minidooray.task.backend.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
    private Long id;

    @Column(name = "milestone_name")
    private String name;

    @Column(name = "milestone_start_date")
    private LocalDate startDate;

    @Column(name = "milestone_end_date")
    private LocalDate endDate;

    @Column(name = "milestone_over_or_not")
    private String overOrNot;

    @ManyToOne
    @JoinColumn(name = "product")
    private Project project;

    public Milestone(String name, LocalDate startDate, LocalDate endDate, String overOrNot, Project project) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.overOrNot = overOrNot;
        this.project = project;
    }

    public Milestone(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public Milestone modify(String name, LocalDate startDate, LocalDate endDate){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;

        return this;
    }
}
