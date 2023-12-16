package ru.cv2.springcars.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.cv2.springcars.models.abstracts.BaseExtended;
import ru.cv2.springcars.models.enums.Engine;
import ru.cv2.springcars.models.enums.Transmission;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Offer extends BaseExtended {
    @NotBlank(message = "Description cannot be blank")
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Engine type must be specified")
    private Engine engine;

    @Pattern(regexp = "^(http|https)://.*\\.(jpg|png|gif|bmp)$", message = "Image URL must be a valid image URL")
    private String imageUrl;

    @NotNull(message = "Mileage must be specified")
    @Min(value = 0, message = "Mileage must be a positive number")
    private Integer mileage;

    @NotNull(message = "Price must be specified")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Transmission type must be specified")
    private Transmission transmission;

    @NotNull(message = "Year of manufacture must be specified")
    @Min(value = 1900, message = "Year of manufacture must be no earlier than 1900")
    @Max(value = 2100, message = "Year of manufacture must be no later than 2100")
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "model_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Model model;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;
}
