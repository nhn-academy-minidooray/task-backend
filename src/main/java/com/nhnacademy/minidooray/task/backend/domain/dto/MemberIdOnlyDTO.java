package com.nhnacademy.minidooray.task.backend.domain.dto;

public interface MemberIdOnlyDTO {
    Pk getPk();

    interface Pk {
        String getAccountId();
    }
}


// 클래스로
// 필드에 변수이름

// 쿼리 어노테이션으로