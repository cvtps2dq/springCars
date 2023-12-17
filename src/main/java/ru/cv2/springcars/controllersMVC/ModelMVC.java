package ru.cv2.springcars.controllersMVC;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.cv2.springcars.models.Brand;
import ru.cv2.springcars.models.Model;
import ru.cv2.springcars.models.Offer;
import ru.cv2.springcars.services.implementation.ModelService;

import java.util.UUID;

@Controller
@RequestMapping("/models")
@Slf4j
public class ModelMVC {

    private ModelService modelService;

    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
    @PostMapping
    public String createBrand(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute Model model) {
        modelService.create(model);
        log.info("User {} invoked model creation",
                userDetails.getUsername());
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String updateBrand(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID id, @ModelAttribute Model model) {
        modelService.update(id, model);
        log.info("User {} invoked model update with id {}",
                userDetails.getUsername(), id);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteBrand(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID id) {
        modelService.delete(id);
        log.info("User {} invoked model deletion with id {}",
                userDetails.getUsername(), id);
        return "redirect:/";
    }
    @GetMapping("/all")
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView("model-list");
        modelAndView.addObject("brands", modelService.getAll());
        log.info("Invoke getAllModels");
        return modelAndView;
    }
}
