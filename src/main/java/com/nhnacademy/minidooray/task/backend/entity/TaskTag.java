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
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`Task-Tag`")
@Builder
@ToString
public class TaskTag {

    @EmbeddedId
    private Pk pk;

    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    @ManyToOne
    private Tag tag;

    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    @ManyToOne
    private Task task;

    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Pk implements Serializable {

        @Column(name = "tag_id")
        private Long tagId;

        @Column(name = "task_id")
        private Long taskId;
    }
}
