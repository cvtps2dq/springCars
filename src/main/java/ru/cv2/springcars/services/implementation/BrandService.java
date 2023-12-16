package ru.cv2.springcars.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.cv2.springcars.mapping.MappingUtility;
import ru.cv2.springcars.models.Brand;
import ru.cv2.springcars.models.dto.BrandDTO;
import ru.cv2.springcars.repos.BrandRepository;
import ru.cv2.springcars.services.BaseService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BrandService implements BaseService<BrandDTO, Brand> {
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

    @Override
    public BrandDTO create(Brand brand) {
        Brand created = brandRepository.save(brand);
        return mappingUtility.convertToDto(created);
    }

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
    @Override
    public BrandDTO update(UUID id, Brand brand) {
        Brand existing = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found!"));
        existing.setName(brand.getName());
        Brand updated = brandRepository.save(existing);
        return mappingUtility.convertToDto(updated);
    }
    @CacheEvict(value = "brand", key = "#id")
    @Override
    public void delete(UUID id) {
        brandRepository.deleteById(id);
    }

    @Override
    public BrandDTO getById(UUID id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(("Brand not found!")));
        return mappingUtility.convertToDto(brand);
    }

    @Cacheable(value = "brands", key = "#root.methodName")
    @Override
    public List<BrandDTO> getAll() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
                .map(mappingUtility::convertToDto)
                .collect(Collectors.toList());
    }

}
