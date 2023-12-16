package ru.cv2.springcars.controllersMVC;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.cv2.springcars.services.implementation.UserService;

@Controller
@RequestMapping("/users")
public class UserMVC {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView("user-list");
        modelAndView.addObject("users", userService.getAll());
        return modelAndView;
    }

    @GetMapping("/top")
    public ModelAndView topSellers() {
        ModelAndView modelAndView = new ModelAndView("top-sellers");
        modelAndView.addObject("users", userService.getAll());
        return modelAndView;
    }
}
