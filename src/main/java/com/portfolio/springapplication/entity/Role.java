package com.portfolio.springapplication.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN", "Administrator"),
    HEAD("ROLE_HEAD", "the Head"),
    USER("ROLE_USER", "User");

    private final String key;
    private final String sub;
}
