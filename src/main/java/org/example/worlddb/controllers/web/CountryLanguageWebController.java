package org.example.worlddb.controllers.web;

import org.example.worlddb.model.entities.CountryLanguageEntity;
import org.example.worlddb.model.repositories.CountryLanguageEntityRepository;
import org.example.worlddb.service.CountryLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/country-languages")
public class CountryLanguageWebController {
    private final CountryLanguageService countryLanguageService;

    @Autowired
    public CountryLanguageWebController(CountryLanguageService countryLanguageService) {
        this.countryLanguageService = countryLanguageService;
    }

    @GetMapping("/")
    public String getCountryLanguages(Model model) {
        model.addAttribute("languages", countryLanguageService.getAllCountryLanguages());
        return "country-language";
    }

    @GetMapping("/delete/{id}")
    public String deleteCountryLanguage(@PathVariable CountryLanguageEntity id) {
        countryLanguageService.getCountryLanguageById(id.getId()).ifPresent(countryLanguage -> countryLanguageService.deleteCountryLanguage(countryLanguage.getId()));
        return "redirect:/country-languages";
    }

    @GetMapping("/edit/{id}")
    public String editCountryLanguage(@PathVariable CountryLanguageEntity id, Model model) {
        CountryLanguageEntity languageToUpdate = countryLanguageService.getCountryLanguageById(id.getId()).orElse(null);
        model.addAttribute("language", languageToUpdate);
        return "/save-edit";
    }

//    @PostMapping("/save-edit/{id}")
//    public String saveCountryLanguage(@PathVariable CountryLanguageEntity id, @ModelAttribute("language") CountryLanguageEntity countryLanguage) {
//
//
//    C}

}
