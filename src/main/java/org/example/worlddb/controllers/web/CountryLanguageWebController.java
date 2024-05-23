package org.example.worlddb.controllers.web;

import org.example.worlddb.service.CountryLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CountryLanguageWebController {
    private final CountryLanguageService countryLanguageService;

    @Autowired
    public CountryLanguageWebController(CountryLanguageService countryLanguageService) {
        this.countryLanguageService = countryLanguageService;
    }


}
