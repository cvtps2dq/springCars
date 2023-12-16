package ru.cv2.springcars.controllersMVC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.cv2.springcars.models.Brand;
import ru.cv2.springcars.services.implementation.BrandService;
import ru.cv2.springcars.services.implementation.ModelService;

import java.util.UUID;

@Controller
@RequestMapping("/brands_list")
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

    @PostMapping
    public String createBrand(@ModelAttribute Brand brand) {
        brandService.create(brand);
        return "redirect:/brands";
    }

    @PutMapping("/{id}")
    public String updateBrand(@PathVariable UUID id, @ModelAttribute Brand brand) {
        brandService.update(id, brand);
        return "redirect:/brands";
    }

    @DeleteMapping("/{id}")
    public String deleteBrand(@PathVariable UUID id) {
        brandService.delete(id);
        return "redirect:/brands";
    }

    @GetMapping("/{id}")
    public ModelAndView getBrandById(@PathVariable UUID id) {
        ModelAndView modelAndView = new ModelAndView("brand-details");
        modelAndView.addObject("brand", brandService.getById(id));
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView getAllBrands() {
        ModelAndView modelAndView = new ModelAndView("brand-list");
        modelAndView.addObject("brands", brandService.getAll());
        return modelAndView;
    }

    @GetMapping("/models/{id}")
    public ModelAndView getAllModelsFromBrand(@PathVariable UUID id) {
        ModelAndView modelAndView = new ModelAndView("brand-models");
        modelAndView.addObject("models", modelService.getAllByBrandId(id));
        return modelAndView;
    }

    @GetMapping("/top")
    public ModelAndView getTopBrands() {
        ModelAndView modelAndView = new ModelAndView("top-brands");
        modelAndView.addObject("brands", brandService.findMostPopularBrands(10));
        return modelAndView;
    }
}