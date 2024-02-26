package ru.cv2.springcars.controllersMVC;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.cv2.springcars.models.Model;
import ru.cv2.springcars.models.Offer;
import ru.cv2.springcars.services.implementation.OfferService;

import java.util.UUID;

@Controller
@RequestMapping("/offers")
@Slf4j
public class OfferMVC {

    private OfferService offerService;

    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    public String createOffer(@AuthenticationPrincipal UserDetails userDetails,@ModelAttribute Offer offer) {
        offerService.create(offer);
        log.info("User {} invoked offer creation",
                userDetails.getUsername());
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String updateOffer(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID id, @ModelAttribute Offer offer) {
        offerService.update(id, offer);
        log.info("User {} invoked offer update with id {}",
                userDetails.getUsername(), id);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteOffer(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID id) {
        offerService.delete(id);
        log.info("User {} invoked offer deletion with id {}",
                userDetails.getUsername(), id);
        return "redirect:/";
    }

    @GetMapping("/all")
    public ModelAndView getAll(@AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView modelAndView = new ModelAndView("offer-list");
        modelAndView.addObject("offers", offerService.getAll());
        log.info("User {} invoked getAllOffers",
                userDetails.getUsername()!=null?userDetails.getUsername():"Unregistered user");
        return modelAndView;
    }
    @GetMapping("/day_pack")
    public ModelAndView dayPack(@AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView modelAndView = new ModelAndView("offer-day-pack");
        modelAndView.addObject("offers", offerService.dayPack());
        log.info("User {} invoked dayPack",
                userDetails.getUsername()!=null?userDetails.getUsername():"Unregistered user");
        return modelAndView;
    }

}
