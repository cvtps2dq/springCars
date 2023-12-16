package ru.cv2.springcars.services;

import java.util.List;
import java.util.UUID;

public interface BaseService<DTO, T> {
    DTO create(T t);
    DTO update(UUID id, T t);
    void delete (UUID id);
    DTO getById(UUID id);
    List<DTO> getAll();
}
