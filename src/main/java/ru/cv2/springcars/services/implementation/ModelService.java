package ru.cv2.springcars.services.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import ru.cv2.springcars.mapping.MappingUtility;
import ru.cv2.springcars.models.Model;
import ru.cv2.springcars.models.dto.ModelDTO;
import ru.cv2.springcars.repos.ModelRepository;
import ru.cv2.springcars.services.BaseService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ModelService implements BaseService<ModelDTO, Model> {

    private ModelRepository modelRepository;
    private MappingUtility mappingUtility;

    @Autowired
    public void setModelRepository(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Autowired
    public void setMappingUtility(MappingUtility mappingUtility) {
        this.mappingUtility = mappingUtility;
    }

    @CachePut(value = "models", key = "#result.id")
    @Override
    public ModelDTO create(Model model) {
        Model created = modelRepository.save(model);
        return mappingUtility.convertToDto(created);
    }

    @CachePut(value = "models", key = "#id")
    @Override
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
    @Override
    public void delete(UUID id) {
        modelRepository.deleteById(id);
    }
    @Cacheable(value = "models", key = "#id")
    @Override
    public ModelDTO getById(UUID id) {
        Model model = modelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Model not found!"));
        return mappingUtility.convertToDto(model);
    }

    @Cacheable(value = "models", key = "#root.methodName")
    @Override
    public List<ModelDTO> getAll() {
        List<Model> models = modelRepository.findAll();
        return models.stream()
                .map(mappingUtility::convertToDto)
                .collect(Collectors.toList());
    }
}
