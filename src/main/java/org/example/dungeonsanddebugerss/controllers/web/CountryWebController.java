package org.example.dungeonsanddebugerss.controllers.web;

import org.example.dungeonsanddebugerss.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CountryWebController {
    private final CountryService countryService;

    @Autowired
    public CountryWebController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/web/countries")
    public String getCountries(Model model) {
        model.addAttribute("countries", countryService.getAllCountries());
        return "countries";
    }

    @GetMapping("/web/country/{code}")
    public String getCountry(@PathVariable String code, Model model) {
        model.addAttribute("country", countryService.getCountryByCode(code));
        return "country";
    }
}
