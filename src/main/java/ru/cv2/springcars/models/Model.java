package ru.cv2.springcars.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.cv2.springcars.models.abstracts.BaseExtended;
import ru.cv2.springcars.models.enums.Category;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Model extends BaseExtended {

    @NotBlank(message = "Model name must not be empty.")
    @Size(max = 128, message = "Model name cannot be longer than 128 symbols")
    private String name;

    @NotNull(message = "Category cannot be null.")
    private Category category;

    @Pattern(regexp = "^(http|https)://.*\\.(jpg|png|gif|bmp)$", message = "Image URL must be a valid image URL")
    private String imageUrl;

    @NotNull(message = "Start year cannot be null.")
    @Min(value = 1900, message = "Start year cannot be less than 1900")
    private Integer startYear;

    @NotNull(message = "End year cannot be null.")
    @Min(value = 1900, message = "End year cannot be less than 1900")
    @Max(value = 2100, message = "End year cannot be bigger than 2100")
    private Integer endYear;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Brand brand;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
    private List<Offer> offers;

}
