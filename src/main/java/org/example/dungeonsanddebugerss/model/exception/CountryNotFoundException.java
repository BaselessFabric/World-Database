package org.example.dungeonsanddebugerss.model.exception;

public class CountryNotFoundException extends Exception {
    public CountryNotFoundException(String country) {
        super("Could not find country: " + country);
    }

}
