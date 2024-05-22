package org.example.dungeonsanddebugerss.model.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.example.dungeonsanddebugerss.model.exception.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CountryExceptionAdvice {

    @ExceptionHandler(CountryNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> countryNotfoundHandler(CountryNotFoundException e, HttpServletRequest request) {
        Response response = new Response(request.getRequestURL().toString(), 400, e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(CountryCodeDoesNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> countryDoesNotExist(CountryCodeDoesNotExistException e, HttpServletRequest request) {
        Response response = new Response(request.getRequestURL().toString(), 400, e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(CountryIsNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> countryIsNull(CountryIsNullException e, HttpServletRequest request) {
        Response response = new Response(request.getRequestURL().toString(), 400, e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


}