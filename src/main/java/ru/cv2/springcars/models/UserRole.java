package ru.cv2.springcars.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.cv2.springcars.models.abstracts.BaseEntity;
import ru.cv2.springcars.models.enums.Role;

import java.util.Set;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@Builder
public class UserRole extends BaseEntity {
    @Enumerated(value = EnumType.STRING)
    @Column
    private Role role;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "role", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<User> users;

}
