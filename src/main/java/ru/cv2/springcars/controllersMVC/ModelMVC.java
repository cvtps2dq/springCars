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

    @GetMapping("/all")
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView("model-list");
        modelAndView.addObject("brands", modelService.getAll());
        log.info("Invoke getAllModels");
        return modelAndView;
    }
}
