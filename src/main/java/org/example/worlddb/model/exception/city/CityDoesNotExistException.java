package org.example.worlddb.model.exception.city;

public class CityDoesNotExistException extends Exception {

    public CityDoesNotExistException(String name){
        super("Could not find city: " + name);
    }
}
