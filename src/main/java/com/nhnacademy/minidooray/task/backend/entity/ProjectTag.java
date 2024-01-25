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
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Project_Tag")
public class ProjectTag {

    @EmbeddedId
    private Pk pk;

    @MapsId("projectId")
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @MapsId("tagId")
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;


    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class Pk implements Serializable {

        @NotNull
        @Column(name = "project_id")
        private Long projectId;

        @NotNull
        @Column(name = "tag_id")
        private Long tagId;
    }
}
