package ru.cv2.springcars.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.cv2.springcars.models.abstracts.BaseEntity;
import ru.cv2.springcars.models.enums.Role;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserRole extends BaseEntity {
    @NotNull(message = "User role cannot be null.")
    @Column(nullable = false)
    private Role userRole;
}
