package ru.cv2.springcars.models.enums;

public enum Role {
    User(0),
    Admin(1);

    final int swCode;

    Role(int swCode){
        this.swCode = swCode;
    }

    public static Role fromCode(int code) {
        for (Role role : values()) {
            if (role.swCode == code) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
