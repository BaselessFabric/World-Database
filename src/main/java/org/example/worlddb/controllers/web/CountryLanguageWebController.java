package org.example.worlddb.controllers.web;

import org.example.worlddb.model.entities.CountryLanguageEntity;
import org.example.worlddb.model.entities.CountryLanguageEntityId;
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

    @GetMapping("")
    public String getCountryLanguages(Model model) {
        model.addAttribute("languages", countryLanguageService.getAllCountryLanguages());
        return "country-language";
    }

    @GetMapping("/delete")
    public String deleteCountryLanguage(@RequestParam String countryCode,@RequestParam String language) {
        countryLanguageService.getCountryLanguageById(countryCode, language).ifPresent(countryLanguage -> countryLanguageService.deleteCountryLanguage(countryLanguage.getId()));
        return "redirect:/web/country-languages";
    }

    @GetMapping("/edit")
    public String editCountryLanguage(@RequestParam String countryCode, @RequestParam String language, Model model) {
        CountryLanguageEntity languageToUpdate = countryLanguageService.getCountryLanguageById(countryCode, language).orElse(null);
        model.addAttribute("language", languageToUpdate);
        return "edit-language";
    }

    @PostMapping("/edit-language")
    public String saveCountryLanguage(@ModelAttribute("language") CountryLanguageEntity countryLanguage) {

    CountryLanguageEntity languageToUpdate = countryLanguageService.getCountryLanguageById(countryLanguage.getId().getCountryCode(), countryLanguage.getId().getLanguage()).orElse(null);
    languageToUpdate.setCountryCode(countryLanguage.getCountryCode());
    languageToUpdate.setPercentage(countryLanguage.getPercentage());
    languageToUpdate.setIsOfficial(countryLanguage.getIsOfficial());
    return "redirect:/web/country-languages";
    }


}
