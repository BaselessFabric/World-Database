package org.example.worlddb.controllers.web;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Logger;

@Controller
public class NavigationWebController {

    @GetMapping("/") //homepage
    public String getHomepage() {
        return "home";
    }



}
