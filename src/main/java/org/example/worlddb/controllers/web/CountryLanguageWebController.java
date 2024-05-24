package org.example.worlddb.controllers.web;

import org.example.worlddb.model.entities.CountryEntity;
import org.example.worlddb.model.entities.CountryLanguageEntity;
import org.example.worlddb.model.entities.CountryLanguageEntityId;
import org.example.worlddb.model.repositories.CountryLanguageEntityRepository;
import org.example.worlddb.service.CountryLanguageService;
import org.example.worlddb.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/country-languages")
public class CountryLanguageWebController {
    private final CountryLanguageService countryLanguageService;
    private final CountryService countryService;

    @Autowired
    public CountryLanguageWebController(CountryLanguageService countryLanguageService, CountryService countryService) {
        this.countryLanguageService = countryLanguageService;
        this.countryService = countryService;
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
        model.addAttribute("languageObj", languageToUpdate);
        model.addAttribute("countryCode", countryCode);
        model.addAttribute("language", language);
        return "edit-language";
    }

    @PostMapping("/edit-language")
    public String saveCountryLanguage(@ModelAttribute("languageObj") CountryLanguageEntity countryLanguage, @ModelAttribute("countryCode") String countryCode, @ModelAttribute("language") String language) {

    CountryLanguageEntity languageToUpdate;
        languageToUpdate = countryLanguageService.getCountryLanguageById(countryCode, language).orElse(null);

        System.out.println(languageToUpdate);
    CountryEntity country = countryService.getCountryByCode(countryCode).orElse(null);
    CountryLanguageEntityId countryLanguageEntityId = new CountryLanguageEntityId();
    countryLanguageEntityId.setCountryCode(countryCode);
    countryLanguageEntityId.setLanguage(language);

    languageToUpdate.setCountryCode(country);
    languageToUpdate.setId(countryLanguageEntityId);
    languageToUpdate.setPercentage(countryLanguage.getPercentage());
    languageToUpdate.setIsOfficial(countryLanguage.getIsOfficial());
    return "redirect:/web/country-languages";
    }


}
