package com.coderscampus.flightTrack.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomErrorController {
	 @ExceptionHandler(HttpClientErrorException.class)
	    public ModelAndView handleHttpClientErrorException(HttpClientErrorException ex) {
	        // Create a ModelAndView for the custom error page
	        ModelAndView modelAndView = new ModelAndView("404error"); // Use the name of your custom HTML file
	        modelAndView.addObject("errorMessage", "Resource not found (404)");
	        return modelAndView;
	    }
}


