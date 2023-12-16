package ru.cv2.springcars.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cv2.springcars.models.Model;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@Repository
public interface ModelRepository extends JpaRepository<Model, UUID> {
    //List<Model> findAllByOrderByStartYearAsc(Pageable pageable);
}
