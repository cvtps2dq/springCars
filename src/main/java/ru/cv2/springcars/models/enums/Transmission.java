package ru.cv2.springcars.models.enums;

public enum Transmission {
    Manual(0),
    Automatic(1);

    final int swCode;

    Transmission(int swCode){
        this.swCode = swCode;
    }

    public static Transmission fromCode(int code) {
        for (Transmission transmission : values()) {
            if (transmission.swCode == code) {
                return transmission;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
