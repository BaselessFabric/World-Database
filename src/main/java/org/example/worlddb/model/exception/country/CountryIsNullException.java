package org.example.worlddb.model.exception.country;

public class CountryIsNullException extends Exception{

    public CountryIsNullException() {
        super("Country provided is null!");
    }
}
