package com.nhnacademy.minidooray.task.backend.domain.dto.milestone;

import java.time.LocalDate;

public interface MilestoneDetailDto {
    Long getId();

    String getName();

    LocalDate getStartDate();

    LocalDate getEndDate();

}
