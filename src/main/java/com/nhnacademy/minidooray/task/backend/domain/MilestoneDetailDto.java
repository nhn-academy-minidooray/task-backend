package com.nhnacademy.minidooray.task.backend.domain;

import java.time.LocalDate;

public interface MilestoneDetailDto {
    Long getId();

    String getName();

    LocalDate getStartDate();

    LocalDate getEndDate();

    String getOverOrNot();

}
