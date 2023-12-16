package ru.cv2.springcars.controllersMVC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.cv2.springcars.services.implementation.OfferService;

@Controller
@RequestMapping("/offers")
public class OfferMVC {

    private OfferService offerService;

    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/all")
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView("offer-list");
        modelAndView.addObject("offers", offerService.getAll());
        return modelAndView;
    }
    @GetMapping("/day_pack")
    public ModelAndView dayPack() {
        ModelAndView modelAndView = new ModelAndView("offer-day-pack");
        modelAndView.addObject("offers", offerService.dayPack());
        return modelAndView;
    }

}
