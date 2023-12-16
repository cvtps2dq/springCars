package ru.cv2.springcars.models.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ModelDTO {
    private UUID id;
    private String name;
    private String category;
    private String imageUrl;
    private Integer startYear;
    private Integer endYear;
    private Date created;
    private Date modified;
    private BrandDTO brand;
}
