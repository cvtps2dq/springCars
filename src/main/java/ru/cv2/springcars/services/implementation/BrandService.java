package ru.cv2.springcars.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.cv2.springcars.mapping.MappingUtility;
import ru.cv2.springcars.models.Brand;
import ru.cv2.springcars.models.dto.BrandDTO;
import ru.cv2.springcars.repos.BrandRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@EnableCaching
public class BrandService {
    private BrandRepository brandRepository;
    private MappingUtility mappingUtility;
    @Autowired
    public void setBrandRepository(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    @Autowired
    public void setMappingUtility(MappingUtility mappingUtility) {
        this.mappingUtility = mappingUtility;
    }

    public BrandDTO create(Brand brand) {
        Brand created = brandRepository.save(brand);
        return mappingUtility.convertToDto(created);
    }

    @Cacheable(value = "brands", key = "#root.methodName")
    public List<BrandDTO> findMostPopularBrands(int limit) {
        List<BrandDTO> brands = brandRepository.findMostPopular().stream()
                .limit(limit)
                .map(mappingUtility::convertToDto)
                .collect(Collectors.toList());

        return brands;
    }

    public List<BrandDTO> alphabetSort(){
        List<BrandDTO> brands =
        brandRepository.findAll().stream()
                .map(mappingUtility::convertToDto)
                .collect(Collectors.toList());

         brands.sort(Comparator.comparing(BrandDTO::getName));
         return brands;
    }
    @CachePut(value = "brands", key = "#id")
    public BrandDTO update(UUID id, Brand brand) {
        Brand existing = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found!"));
        existing.setName(brand.getName());
        Brand updated = brandRepository.save(existing);
        return mappingUtility.convertToDto(updated);
    }
    @CacheEvict(value = "brands", key = "#id")
    public void delete(UUID id) {
        brandRepository.deleteById(id);
    }

    public BrandDTO getById(UUID id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(("Brand not found!")));
        return mappingUtility.convertToDto(brand);
    }

    @Cacheable(value = "brands", key = "#root.methodName")
    public List<BrandDTO> getAll() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
                .map(mappingUtility::convertToDto)
                .collect(Collectors.toList());
    }

}
