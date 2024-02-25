package ru.cv2.springcars.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.cv2.springcars.mapping.MappingUtility;
import ru.cv2.springcars.models.UserRole;
import ru.cv2.springcars.models.dto.UserRoleDTO;
import ru.cv2.springcars.models.enums.Role;
import ru.cv2.springcars.repos.UserRoleRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class UserRoleService {
    private UserRoleRepository userRoleRepository;
    private MappingUtility mappingUtility;
    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Autowired
    public void setMappingUtility(MappingUtility mappingUtility) {
        this.mappingUtility = mappingUtility;
    }

    @CachePut(value = "userRoles", key = "#result.id")
    public UserRoleDTO create(UserRole userRole) {
        UserRole created = userRoleRepository.save(userRole);
        return mappingUtility.convertToDto(created);
    }

    @CachePut(value = "userRoles", key = "#id")
    public UserRoleDTO update(UUID id, UserRole userRole) {
        UserRole existing = userRoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserRole not found"));

        existing.setRole(userRole.getRole());

        UserRole updated = userRoleRepository.save(existing);
        return mappingUtility.convertToDto(updated);
    }

    public UserRoleDTO findByRole(Role role) {
        return mappingUtility.convertToDto(userRoleRepository.findUserRoleByRole(role).orElseThrow(() -> new NoSuchElementException("No such role " + role)));
    }

    @CacheEvict(value = "userRoles", key = "#id")
    public void delete(UUID id) {
        userRoleRepository.deleteById(id);
    }
    @Cacheable(value = "userRoles", key = "#id")
    public UserRoleDTO getById(UUID id) {
        UserRole userRole = userRoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserRole not found!"));
        return mappingUtility.convertToDto(userRole);
    }

    @Cacheable(value = "userRoles", key = "#root.methodName")
    public List<UserRoleDTO> getAll() {
        List<UserRole> userRoles = userRoleRepository.findAll();
        return userRoles.stream()
                .map(mappingUtility::convertToDto)
                .collect(Collectors.toList());
    }
}
