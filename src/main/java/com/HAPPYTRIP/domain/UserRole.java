package com.HAPPYTRIP.domain;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    //역할 이름을 저장하는 필드
    UserRole(String value) {
        this.value = value;
    }

    //역할 이름을 초기화하는 생성자
    private String value;
}