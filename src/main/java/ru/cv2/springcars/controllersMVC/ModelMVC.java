package ru.cv2.springcars.controllersMVC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.cv2.springcars.services.implementation.ModelService;

@Controller
@RequestMapping("/models")
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
        return modelAndView;
    }
}
