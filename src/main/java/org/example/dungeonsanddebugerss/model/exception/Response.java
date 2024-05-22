package org.example.dungeonsanddebugerss.model.exception;

public record Response (String message, int statusCode, String url) {
}
