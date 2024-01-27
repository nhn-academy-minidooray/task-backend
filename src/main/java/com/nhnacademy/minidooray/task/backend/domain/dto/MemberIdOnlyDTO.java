package com.nhnacademy.minidooray.task.backend.domain.dto;

public interface MemberIdOnlyDTO {
    Pk getPk();

    interface Pk {
        String getAccountId();
    }
}
