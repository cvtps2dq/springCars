package ru.cv2.springcars.controllersMVC;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageMVC {
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
