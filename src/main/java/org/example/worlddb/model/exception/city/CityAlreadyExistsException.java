package org.example.worlddb.model.exception.city;

public class CityAlreadyExistsException extends Exception{

    public CityAlreadyExistsException(int cityId) {
        super("City with ID " + cityId + " already exists.");
    }
}
