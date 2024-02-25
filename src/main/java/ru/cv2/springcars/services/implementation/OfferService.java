package ru.cv2.springcars.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import ru.cv2.springcars.mapping.MappingUtility;
import ru.cv2.springcars.models.Offer;
import ru.cv2.springcars.models.dto.OfferDTO;
import ru.cv2.springcars.repos.OfferRepository;

import java.util.*;
import java.util.stream.Collectors;
@Service
@EnableCaching
public class OfferService {
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
    public OfferDTO create(Offer offer) {
        Offer created = offerRepository.save(offer);
        return mappingUtility.convertToDto(created);
    }

    @CachePut(value = "offers", key = "#id")
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
    public void delete(UUID id) {
        offerRepository.deleteById(id);
    }
    @Cacheable(value = "offers", key = "#id")
    public OfferDTO getById(UUID id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found!"));
        return mappingUtility.convertToDto(offer);
    }

    @Cacheable(value = "offers", key = "#root.methodName")
    public List<OfferDTO> getAll() {
        List<Offer> offers = offerRepository.findAll();
        return offers.stream()
                .map(mappingUtility::convertToDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "offers", key = "#root.methodName")
    public List<OfferDTO> dayPack(){
        Random random = new Random();
        List<OfferDTO> result = new ArrayList<>();
        List<OfferDTO> input = offerRepository.findAll().stream()
                .map(mappingUtility::convertToDto)
                .collect(Collectors.toList());

        input.sort(Comparator.comparing(OfferDTO::getPrice));
        // Get random lower values
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            result.add(input.get(rand.nextInt(input.size() / 3))); // Lower third
        }

        // Get mid values
        for (int i = 0; i < 3; i++) {
            result.add(input.get(input.size() / 2 + rand.nextInt(input.size() / 3))); // Middle third
        }

        // Get high values
        for (int i = 0; i < 3; i++) {
            result.add(input.get(input.size() - 1 - rand.nextInt(input.size() / 3))); // Upper third
        }

        for (OfferDTO offerDTO : result){
            System.out.println(offerDTO);
        }

        return result;
    }
}
