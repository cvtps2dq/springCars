package ru.cv2.springcars.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.cv2.springcars.mapping.MappingUtility;
import ru.cv2.springcars.models.dto.UserDTO;
import ru.cv2.springcars.repos.UserRespository;
import ru.cv2.springcars.services.BaseService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class UserService implements BaseService<UserDTO, User> {
    private UserRespository userRepository;
    private MappingUtility mappingUtility;
    @Autowired
    public void setuserRepository(UserRespository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setMappingUtility(MappingUtility mappingUtility) {
        this.mappingUtility = mappingUtility;
    }

    @CachePut(value = "users", key = "#result.id")
    @Override
    public UserDTO create(User user) {
        User created = userRepository.save(user);
        return mappingUtility.convertToDto(created);
    }

    @CachePut(value = "users", key = "#id")
    @Override
    public UserDTO update(UUID id, User user) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setUsername(user.getUsername());
        existing.setImageUrl(user.getImageUrl());
        existing.setIsActive(user.getIsActive());
        existing.setLastName(user.getLastName());
        existing.setFirstName(user.getFirstName());

        User updated = userRepository.save(existing);
        return mappingUtility.convertToDto(updated);
    }


    @CacheEvict(value = "users", key = "#id")
    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
    @Cacheable(value = "users", key = "#id")
    @Override
    public UserDTO getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        return mappingUtility.convertToDto(user);
    }

    @Cacheable(value = "users", key = "#root.methodName")
    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(mappingUtility::convertToDto)
                .collect(Collectors.toList());
    }
}
