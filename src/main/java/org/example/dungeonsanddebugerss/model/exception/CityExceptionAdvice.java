package org.example.dungeonsanddebugerss.model.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CityExceptionAdvice {

    @ExceptionHandler(CityAlreadyExistsException.class)
    public ResponseEntity<Response> cityAlreadyExistsHandler(CityAlreadyExistsException e, HttpServletRequest request) {
        Response response = new Response(e.getMessage(),
                                         HttpStatus.BAD_REQUEST.value(),
                                         request.getRequestURL().toString());
        return ResponseEntity.badRequest()
                             .body(response);
    }

    @ExceptionHandler(CountryDoesNotExistException.class)
    public ResponseEntity<Response> countryDoesNotExistHandler(CountryDoesNotExistException e, HttpServletRequest request) {
        Response response = new Response(e.getMessage(),
                                         HttpStatus.BAD_REQUEST.value(),
                                         request.getRequestURL().toString());
        return ResponseEntity.badRequest()
                             .body(response);
    }

    @ExceptionHandler(CityDoesNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> cityDoesNotExistHandler(CityDoesNotExistException e, HttpServletRequest request){

        Response response = new Response(e.getMessage(), 400, request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }
}
