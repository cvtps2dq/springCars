package ru.cv2.springcars.models.enums;

public enum Category {
    Car(0),
    Bus(1),
    Truck(2),
    Motorcycle(3);

    final int swCode;

    Category(int swCode){
        this.swCode = swCode;
    }

    public static Category fromCode(int code) {
        for (Category category : values()) {
            if (category.swCode == code) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
