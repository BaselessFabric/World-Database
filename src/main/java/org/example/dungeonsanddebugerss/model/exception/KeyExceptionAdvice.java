package org.example.dungeonsanddebugerss.model.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class KeyExceptionAdvice {

    @ExceptionHandler(KeyNotFoundException.class)
    public ResponseEntity<Response> KeyNotFoundHandler(KeyNotFoundException e, HttpServletRequest request) {
        Response response = new Response(e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                request.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
