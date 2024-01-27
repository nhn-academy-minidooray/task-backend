package com.nhnacademy.minidooray.task.backend.domain;

public enum Status {
    ACTIVATION("활성"),
    DORMANT("휴면"),
    END("종료");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
