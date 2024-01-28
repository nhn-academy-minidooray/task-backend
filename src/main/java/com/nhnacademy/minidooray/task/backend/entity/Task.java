package com.nhnacademy.minidooray.task.backend.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Task")
@Builder
@ToString
@EqualsAndHashCode
public class Task {

    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_name")
    private String name;

    @Column(name = "task_detail")
    private String detail;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToOne
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    private List<TaskTag> taskTags;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    public void modify(String name, Milestone milestone) {
        this.name = name;
        this.milestone = milestone;
    }


}