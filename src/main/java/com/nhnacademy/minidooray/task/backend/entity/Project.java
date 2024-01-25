package com.nhnacademy.minidooray.task.backend.entity;

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
@Table(name = "Project")
public class Project {

    @Id
    @NotNull
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long projectId;

    @NotNull
    @Column(name = "project_name")
    String projectName;

    @NotNull
    @Column(name = "project_status")
    String projectStatus;

    @NotNull
    @Column(name = "project_admin_id")
    String projectAdminId; //타입 확인
}
