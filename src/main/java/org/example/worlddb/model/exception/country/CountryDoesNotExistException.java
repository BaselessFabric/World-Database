package org.example.worlddb.model.exception.country;

public class CountryDoesNotExistException extends Exception{

    public CountryDoesNotExistException(String countryCode) {
        super("Cannot find country with code: " + countryCode);
    }
}
