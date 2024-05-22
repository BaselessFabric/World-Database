package org.example.dungeonsanddebugerss.model.exception;

import org.example.dungeonsanddebugerss.model.entities.CountryEntity;

public class CountryIsNullException extends Exception{

    public CountryIsNullException() {
        super("Country provided is null!");
    }
}
