package ru.cv2.springcars.controllersMVC;

import jdk.jshell.spi.ExecutionControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.cv2.springcars.services.implementation.UserService;

@Controller
@RequestMapping("/users")
@Slf4j
public class UserMVC {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ModelAndView getAll(@AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView modelAndView = new ModelAndView("user-list");
        modelAndView.addObject("users", userService.getAll());
        log.info("User {} invoked getAll user",
                userDetails.getUsername()!=null?userDetails.getUsername():"Unregistered user");
        return modelAndView;
    }

    @GetMapping("/top")
    public ModelAndView topSellers(@AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView modelAndView = new ModelAndView("top-sellers");
        modelAndView.addObject("users", userService.getAll());
        log.info("User {} invoked getAll user",
                userDetails.getUsername()!=null?userDetails.getUsername():"Unregistered user");
        return modelAndView;
    }
}
