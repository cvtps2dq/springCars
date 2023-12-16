package ru.cv2.springcars.models.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class BrandDTO {
    private UUID id;
    private String name;
    private Date created;
    private Date modified;
}
