package ru.cv2.springcars.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.cv2.springcars.mapping.MappingUtility;
import ru.cv2.springcars.models.Offer;
import ru.cv2.springcars.models.dto.OfferDTO;
import ru.cv2.springcars.repos.OfferRepository;
import ru.cv2.springcars.services.BaseService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class OfferService implements BaseService<OfferDTO, Offer> {
    private OfferRepository offerRepository;
    private MappingUtility mappingUtility;
    @Autowired
    public void setofferRepository(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Autowired
    public void setMappingUtility(MappingUtility mappingUtility) {
        this.mappingUtility = mappingUtility;
    }

    @CachePut(value = "offers", key = "#result.id")
    @Override
    public OfferDTO create(Offer offer) {
        Offer created = offerRepository.save(offer);
        return mappingUtility.convertToDto(created);
    }

    @CachePut(value = "offers", key = "#id")
    @Override
    public OfferDTO update(UUID id, Offer offer) {
        Offer existing = offerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        existing.setDescription(offer.getDescription());
        existing.setEngine(offer.getEngine());
        existing.setImageUrl(offer.getImageUrl());
        existing.setMileage(offer.getMileage());
        existing.setPrice(offer.getPrice());
        existing.setTransmission(offer.getTransmission());
        existing.setYear(offer.getYear());
        existing.setModel(offer.getModel());
        existing.setSeller(offer.getSeller());

        Offer updated = offerRepository.save(existing);
        return mappingUtility.convertToDto(updated);
    }


    @CacheEvict(value = "offers", key = "#id")
    @Override
    public void delete(UUID id) {
        offerRepository.deleteById(id);
    }
    @Cacheable(value = "offers", key = "#id")
    @Override
    public OfferDTO getById(UUID id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found!"));
        return mappingUtility.convertToDto(offer);
    }

    @Cacheable(value = "offers", key = "#root.methodName")
    @Override
    public List<OfferDTO> getAll() {
        List<Offer> offers = offerRepository.findAll();
        return offers.stream()
                .map(mappingUtility::convertToDto)
                .collect(Collectors.toList());
    }
}
