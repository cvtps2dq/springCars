package ru.cv2.springcars.services.implementation;

import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.cv2.springcars.mapping.MappingUtility;
import ru.cv2.springcars.models.Offer;
import ru.cv2.springcars.models.User;
import ru.cv2.springcars.models.dto.OfferDTO;
import ru.cv2.springcars.models.dto.UserDTO;
import ru.cv2.springcars.models.enums.Role;
import ru.cv2.springcars.repos.OfferRepository;
import ru.cv2.springcars.repos.UserRespository;
import ru.cv2.springcars.services.BaseService;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service
@EnableCaching
public class UserService implements BaseService<UserDTO, User> {
    private UserRespository userRepository;
    private MappingUtility mappingUtility;
    private OfferRepository offerRepository;
    private UserRoleService userRoleService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Autowired
    public void setOfferRepository(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Autowired
    public void setUserRepository(UserRespository userRepository) {
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

    public List<UserDTO> findAllByUsername(String username) {
        return userRepository.findAllByUsername(username)
                .stream()
                .map(mappingUtility::convertToDto)
                .toList();
    }

    private UserDTO saveOrUpdate(UserDTO userDTO) throws EntityExistsException{

        userDTO.setModified(LocalDateTime.now().toString());
        try {
            return mappingUtility.convertToDto(userRepository.saveAndFlush(mappingUtility.convertToEntity(userDTO)));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
        }
    }

    @CachePut(value = "users", key = "#result.id")
    public UserDTO registerNewUser(UserDTO userDTO) throws EntityExistsException {
        if(userRepository.findAllByUsername(userDTO.getUsername()).isEmpty()) {
            userDTO.setUserRoleDTO(userRoleService.findByRole(Role.USER));
            userDTO.setCreated(LocalDateTime.now().toString());
            userDTO.setIsActive(true);
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            return saveOrUpdate(userDTO);
        } else {
            throw new EntityExistsException(MessageFormat.format("User with username {0} already exists", userDTO.getUsername()));
        }
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
