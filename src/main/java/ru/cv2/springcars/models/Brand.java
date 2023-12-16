package ru.cv2.springcars.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import ru.cv2.springcars.models.abstracts.BaseExtended;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "brand")
public class Brand extends BaseExtended {
    @NotBlank(message = "Brand name cannot be empty.")
    @Size(min = 2, max = 64, message = "Brand name must be in [2 <-> 64] length.")
    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Model> models;
}
