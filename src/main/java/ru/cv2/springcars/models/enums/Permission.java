package ru.cv2.springcars.models.enums;

import lombok.Getter;

@Getter
public enum Permission {
    ADMIN_WRITE("admin:write"),
    ADMIN_WATCH("admin:read"),
    USER_WRITE("user:write"),
    USER_WATCH("user:read"),
    SELLER_WATCH("seller:read"),
    SELLER_WRITE("seller:read");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
