package org.example.dungeonsanddebugerss.model.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CountryLanguageAdvice {

    @ExceptionHandler(CountryLanguageNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> countryLanguageNotFoundHandler(CountryLanguageNotFoundException e, HttpServletRequest request){
        Response response = new Response(e.getMessage(), 400, request.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(LanguageAlreadyExistsForCountryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> languageAlreadyExistsForCountryHandler(LanguageAlreadyExistsForCountryException e, HttpServletRequest request){
        Response response = new Response(e.getMessage(), 400, request.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
