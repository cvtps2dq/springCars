package ru.cv2.springcars.services.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import ru.cv2.springcars.mapping.MappingUtility;
import ru.cv2.springcars.models.Brand;
import ru.cv2.springcars.models.Model;
import ru.cv2.springcars.models.dto.BrandDTO;
import ru.cv2.springcars.models.dto.ModelDTO;
import ru.cv2.springcars.repos.BrandRepository;
import ru.cv2.springcars.repos.ModelRepository;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@EnableCaching
public class ModelService {

    private ModelRepository modelRepository;
    private MappingUtility mappingUtility;
    private BrandRepository brandRepository;

    @Autowired
    public void setBrandRepository(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Autowired
    public void setModelRepository(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Autowired
    public void setMappingUtility(MappingUtility mappingUtility) {
        this.mappingUtility = mappingUtility;
    }

    @CachePut(value = "models", key = "#result.id")
    public ModelDTO create(Model model) {
        Model created = modelRepository.save(model);
        return mappingUtility.convertToDto(created);
    }

    @Cacheable(value = "models", key = "#brandId")
    public List<ModelDTO> getAllByBrandId(UUID brandId){

        List<ModelDTO> models = modelRepository.findAllByBrandId(brandId).stream()
                .map(mappingUtility::convertToDto)
                .collect(Collectors.toList());

        models.sort(Comparator.comparing(ModelDTO::getName));

        return models;

    }

    @CachePut(value = "models", key = "#id")
    public ModelDTO update(UUID id, Model model) {
        Model existing = modelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Model not found"));

        existing.setName(model.getName());
        existing.setCategory(model.getCategory());
        existing.setImageUrl(model.getImageUrl());
        existing.setStartYear(model.getStartYear());
        existing.setEndYear(model.getEndYear());
        existing.setBrand(model.getBrand());

        Model updated = modelRepository.save(existing);
        return mappingUtility.convertToDto(updated);
    }


    @CacheEvict(value = "models", key = "#id")
    public void delete(UUID id) {
        modelRepository.deleteById(id);
    }
    @Cacheable(value = "models", key = "#id")
    public ModelDTO getById(UUID id) {
        Model model = modelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Model not found!"));
        return mappingUtility.convertToDto(model);
    }

    @Cacheable(value = "models", key = "#root.methodName")
    public List<ModelDTO> getAll() {
        List<Model> models = modelRepository.findAll();
        return models.stream()
                .map(mappingUtility::convertToDto)
                .collect(Collectors.toList());
    }

}
