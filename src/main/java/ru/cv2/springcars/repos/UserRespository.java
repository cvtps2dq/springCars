package ru.cv2.springcars.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cv2.springcars.models.User;

import java.util.UUID;

@Repository
public interface UserRespository extends JpaRepository<User, UUID> {
}
