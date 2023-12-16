package ru.cv2.springcars.models.enums;

public enum Engine {
    Gasoline(0),
    Diesel(1),
    Electric(2),
    Hybrid(3);

    final int swCode;

    Engine(int swCode){
        this.swCode = swCode;
    }

    public static Engine fromCode(int code) {
        for (Engine engine : values()) {
            if (engine.swCode == code) {
                return engine;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
