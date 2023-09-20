package com.coderscampus.flightTrack.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "404error"; // Return the name of the HTML template for the 404 error page
    }

    
    public String getErrorPath() {
        return "/404error";
    }
}


