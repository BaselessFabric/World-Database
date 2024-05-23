package org.example.worlddb.controllers.web;

import org.example.worlddb.model.entities.CountryEntity;
import org.example.worlddb.model.repositories.CountryEntityRepository;
import org.example.worlddb.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCountry(@PathVariable String code) {
        CountryEntity countryToDelete = countryService.getCountryByCode(code).orElse(null);
        if (countryToDelete != null) {
            countryEntityRepository.delete(countryToDelete);
        }
        return "redirect:/web/countries";
    }

    @GetMapping("/web/add-country")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addBook(Model model) {
        CountryEntity country = new CountryEntity();
        model.addAttribute("country", country);
        return "add-country";
    }
    @PostMapping("/web/save-country")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveCountry(@ModelAttribute("country") CountryEntity country) {
        countryEntityRepository.save(country);
        return "redirect:/web/countries";
    }

    @PostMapping("/web/country/update/{code}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateCountry(@PathVariable String code,String newName,String newContinent,String newRegion, Integer newCapital) {
        CountryEntity updatedCountry = new CountryEntity();
       updatedCountry.setName(newName);
       updatedCountry.setContinent(newContinent);
       updatedCountry.setRegion(newRegion);
       updatedCountry.setSurfaceArea(updatedCountry.getSurfaceArea());
       updatedCountry.setIndepYear(updatedCountry.getIndepYear());
       updatedCountry.setPopulation(updatedCountry.getPopulation());
       updatedCountry.setLifeExpectancy(updatedCountry.getLifeExpectancy());
       updatedCountry.setGnp(updatedCountry.getGnp());
       updatedCountry.setGNPOld(updatedCountry.getGNPOld());
       updatedCountry.setLocalName(updatedCountry.getLocalName());
       updatedCountry.setGovernmentForm(updatedCountry.getGovernmentForm());
       updatedCountry.setHeadOfState(updatedCountry.getHeadOfState());
       updatedCountry.setCapital(updatedCountry.getCapital());
       updatedCountry.setCode2(updatedCountry.getCode2());
        countryService.updateCountry(code, updatedCountry);
        return "redirect:/web/countries";
    }


}
