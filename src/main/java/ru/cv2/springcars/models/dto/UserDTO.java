package ru.cv2.springcars.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    @NotNull
    @NotEmpty
    @Size(min = 8, message = "Username must contains minimum 8 symbols")
    private String username;
    @NotNull
    @NotEmpty
    @Size(min = 8, message = "Password must contains minimum 8 symbols")
    private String password;
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    private Boolean isActive;
    private String imageUrl;
    private LocalDateTime created;
    private LocalDateTime modified;
    private UserRoleDTO userRoleDTO;
}