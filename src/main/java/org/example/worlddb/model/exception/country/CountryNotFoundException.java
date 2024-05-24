package org.example.worlddb.model.exception.country;

public class CountryNotFoundException extends Exception {
    public CountryNotFoundException(String country) {
        super("Could not find country: " + country);
    }

}
