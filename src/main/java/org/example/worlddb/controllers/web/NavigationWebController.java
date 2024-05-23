package org.example.worlddb.controllers.web;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationWebController {

    @GetMapping("/") //homepage
    public String getHomepage() {
        return "home";
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return "error-401";
            }

            if(statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error-403";
            }
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404";
            }

            if(statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
                return "error-405";
            }

            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500";
            }
        }

        return "error";
    }

}
