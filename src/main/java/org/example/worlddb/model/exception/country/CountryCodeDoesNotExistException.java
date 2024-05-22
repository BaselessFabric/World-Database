package org.example.worlddb.model.exception.country;

public class CountryCodeDoesNotExistException extends Exception  {
    public CountryCodeDoesNotExistException(String countryCode) {
        super("Country code " + countryCode + " does not exist");
    }
}
