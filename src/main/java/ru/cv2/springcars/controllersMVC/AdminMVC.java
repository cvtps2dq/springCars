package ru.cv2.springcars.controllersMVC;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.cv2.springcars.mapping.MappingUtility;
import ru.cv2.springcars.models.Brand;
import ru.cv2.springcars.models.Model;
import ru.cv2.springcars.models.dto.BrandDTO;
import ru.cv2.springcars.models.dto.ModelDTO;
import ru.cv2.springcars.models.dto.UserDTO;
import ru.cv2.springcars.models.enums.Category;
import ru.cv2.springcars.services.implementation.BrandService;
import ru.cv2.springcars.services.implementation.ModelService;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminMVC {

    private BrandService brandService;
    private ModelService modelService;

    private MappingUtility mappingUtility;

    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    @ModelAttribute("brandModel")
    public BrandDTO initBrand() {
        return new BrandDTO();
    }

    @ModelAttribute("modelModel")
    public ModelDTO initModel() {
        return new ModelDTO();
    }

    @RequestMapping (value = "/brands/create", method = RequestMethod.POST)
    public String createBrand(Brand brand, RedirectAttributes redirectAttributes, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("create!");
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("brandModel", brand);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.brandModel",
                    bindingResult);
            return "redirect:/admin/panel";
        }
        try {
            brandService.create(brand);
            log.info("User with username {} saved new brand {}", userDetails.getUsername(), brand.getName());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка создания бренда. Бренд уже существует");
            redirectAttributes.addFlashAttribute("brandModel", brand);
            return "redirect:/";
        }
        return "redirect:/admin/panel";
    }

    @RequestMapping(value = "/brands/create", method = RequestMethod.GET)
    public String getCreateBrand(org.springframework.ui.Model model){
        model.addAttribute(initBrand());
        return "create-brand";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/brands/update/"+"{id}")
    public String updateBrand(@PathVariable("id") String id, org.springframework.ui.Model model) {
        System.out.println(id);
        model.addAttribute("brandModel",brandService.getById(UUID.fromString(id)));
        return "update-brand";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/panel")
    public String adminPanel(){
        return "admin-panel";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/brands/update/"+"{id}")
    public String sendUpdate(@Valid Brand brand, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("brandModel", brand);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.brandModel",
                    bindingResult);
            return "redirect:/admin/panel";
        }
        try {
            brandService.update(brand.getId(), brand);
            log.info("User with username {} successfully edited brand to {}", userDetails.getUsername(), brand);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка создания бренда. Бренд уже существует");
            redirectAttributes.addFlashAttribute("brandModel", brand);
            return "redirect:/admin/panel";
        }
        return "redirect:/admin/panel";
    }

    @RequestMapping(value = "/models/create", method = RequestMethod.GET)
    public String createModel(org.springframework.ui.Model model) {
        model.addAttribute("existingCategory", Arrays.stream(Category.values()).toList());
        model.addAttribute("brands", brandService.getAll());
        return "create-model";
    }

    @RequestMapping(value = "/models/update", method = RequestMethod.POST)
    public String saveModel(@Valid Model model) {
        System.out.println(model);
        System.out.println(brandService.getById(model.getBrand().getId()));
        Model modelSave = new Model();
        modelSave.setBrand(mappingUtility.convertToEntity((brandService.getById(model.getBrand().getId()))));
        modelSave.setEndYear(model.getEndYear());
        modelSave.setStartYear(model.getStartYear());
        modelSave.setImageUrl(model.getImageUrl());
        modelSave.setCategory(model.getCategory());
        modelSave.setName(model.getName());
        modelService.create(modelSave);
        return "redirect:/model/all";
    }

    @RequestMapping(value = "/models/update"+"{id}", method = RequestMethod.GET)
    public String editModel(org.springframework.ui.Model model, @PathVariable("id") String id) {
        model.addAttribute("model", modelService.getById(UUID.fromString(id)));
        return "update-model";
    }

}
