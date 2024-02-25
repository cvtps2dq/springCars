package ru.cv2.springcars.controllersMVC;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.cv2.springcars.models.Brand;
import ru.cv2.springcars.models.Offer;
import ru.cv2.springcars.services.implementation.BrandService;
import ru.cv2.springcars.services.implementation.ModelService;

import java.util.UUID;

@Controller
@RequestMapping("/brands_list")
@Slf4j
public class BrandListMVC {

    private BrandService brandService;
    private ModelService modelService;

    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }


    @GetMapping("/{id}")
    public ModelAndView getBrandById(@PathVariable UUID id) {
        ModelAndView modelAndView = new ModelAndView("brand-details");
        modelAndView.addObject("brand", brandService.getById(id));
        log.info("Invoke getBrandById -> {}", id);
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView getAllBrands() {
        ModelAndView modelAndView = new ModelAndView("brand-list");
        modelAndView.addObject("brands", brandService.getAll());
        log.info("Invoke getAllBrands");
        return modelAndView;
    }

    @GetMapping("/models/{id}")
    public ModelAndView getAllModelsFromBrand(@PathVariable UUID id) {
        ModelAndView modelAndView = new ModelAndView("brand-models");
        modelAndView.addObject("models", modelService.getAllByBrandId(id));
        log.info("Invoke getAllModelsFromBrand -> {}", id);
        return modelAndView;
    }

    @GetMapping("/top")
    public ModelAndView getTopBrands() {
        ModelAndView modelAndView = new ModelAndView("top-brands");
        modelAndView.addObject("brands", brandService.findMostPopularBrands(10));
        log.info("Invoke getTopBrands");
        return modelAndView;
    }
}
