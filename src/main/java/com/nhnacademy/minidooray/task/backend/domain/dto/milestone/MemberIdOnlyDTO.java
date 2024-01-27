package com.nhnacademy.minidooray.task.backend.domain.dto.milestone;

public interface MemberIdOnlyDTO {
    Pk getPk();

    interface Pk {
        String getAccountId();
    }
}
