package com.mihir.musiclibrary.user.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    ROLE_ADMIN,
    ROLE_EDITOR,
    ROLE_VIEWER;

    @JsonCreator
    public static UserRole fromValue(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Invalid role: " + value);
        }
        return UserRole.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name();
    }

}
