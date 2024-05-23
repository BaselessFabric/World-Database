package org.example.worlddb.controllers.web;

import org.example.worlddb.model.entities.CountryEntity;
import org.example.worlddb.model.repositories.CountryEntityRepository;
import org.example.worlddb.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
public class CountryWebController {
    private final CountryService countryService;
    private final CountryEntityRepository countryEntityRepository;

    @Autowired
    public CountryWebController(CountryService countryService, CountryEntityRepository countryEntityRepository) {
        this.countryService = countryService;
        this.countryEntityRepository = countryEntityRepository;
    }

    @GetMapping("/web/countries")
    public String getCountries(Model model) {
        model.addAttribute("countries", countryService.getAllCountries());
        return "countries";
    }

    @GetMapping("/web/country/{code}")
    public String getCountry(@PathVariable String code, Model model) {
        model.addAttribute("country", countryService.getCountryByCode(code).orElse(null));
        return "country";
    }

    @GetMapping("/web/country/delete/{code}")
    public String deleteCountry(@PathVariable String code) {
        CountryEntity countryToDelete = countryService.getCountryByCode(code).orElse(null);
        if (countryToDelete != null) {
            countryEntityRepository.delete(countryToDelete);
        }
        return "redirect:/web/countries";
    }

    @GetMapping("/web/country/add")
    public String addCountry(Model model) {
        CountryEntity country = new CountryEntity();
        model.addAttribute("country", country);
        return "add-country";
    }
    @PostMapping("/web/country/save-country")
    public String saveCountry(@ModelAttribute("country") CountryEntity country) {
        //countryEntityRepository.save(country);
        countryService.createCountry(country);
        return "redirect:/web/countries";
    }

    // show the html page with the edit form
    @GetMapping("/web/country/edit/{code}")     //Yall good!!  //BUILDING  //test the edit
    public String editCountry(@PathVariable String code, Model model) {
        Optional<CountryEntity> country = countryService.getCountryByCode(code);
        if (country.isPresent()) {
            model.addAttribute("country", country);
        }
        return "country-edit";
    }

    @PostMapping("/web/country/update/{code}")
            public String updateCountry(@ModelAttribute("country") CountryEntity country){
        countryService.updateCountry(country.getCode(), country);
        return "redirect:/web/countries";
    }


}
