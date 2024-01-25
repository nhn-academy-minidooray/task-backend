package com.nhnacademy.minidooray.task.backend.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Project_Milestone")
public class ProjectMilestone {

    @EmbeddedId
    private Pk pk;

    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    @ManyToOne
    private Project project;

    @MapsId("milestoneId")
    @JoinColumn(name = "milestone_id")
    @ManyToOne
    private Milestone milestone;


    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk implements Serializable {

        @Column(name = "project_id")
        private Long projectId;

        @Column(name = "milestone_id")
        private Long milestoneId;
    }
}
