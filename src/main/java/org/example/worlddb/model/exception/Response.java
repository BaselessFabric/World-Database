package org.example.worlddb.model.exception;

public record Response (String message, int statusCode, String url) {
}
