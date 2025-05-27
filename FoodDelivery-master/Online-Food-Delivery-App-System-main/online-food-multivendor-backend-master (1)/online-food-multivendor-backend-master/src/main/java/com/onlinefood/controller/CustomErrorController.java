package com.onlinefood.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<String> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return new ResponseEntity<>("Page not found. Please check the URL or go to /api endpoints.", 
                                           HttpStatus.NOT_FOUND);
            } else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return new ResponseEntity<>("Internal server error. Please check server logs.", 
                                           HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("An error occurred. Please check the URL or contact support.", 
                                   HttpStatus.BAD_REQUEST);
    }
} 