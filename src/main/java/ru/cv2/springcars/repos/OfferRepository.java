package ru.cv2.springcars.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cv2.springcars.models.Offer;

import java.util.UUID;

public interface OfferRepository extends JpaRepository<Offer, UUID> {
}
