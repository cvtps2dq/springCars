package ru.cv2.springcars.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.cv2.springcars.models.abstracts.BaseExtended;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseExtended {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 32, message = "Username needs to be in range [2 <-> 32]!")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password needs to be at least 8 chars.")
    private String password;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 3, max = 32, message = "First name needs to be in range [2 <-> 32]!")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 3, max = 32, message = "Last name needs to be in range [2 <-> 32]!")
    private String lastName;

    @NotNull(message = "Active status must be specified.")
    private Boolean isActive;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id")
    private UserRole role;

    @Pattern(regexp = "^(http|https)://.*\\.(jpg|png|gif|bmp)$", message = "Image URL must be a valid image URL")
    private String imageUrl;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isActive=" + isActive +
                ", role=" + role +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
