package org.example.dungeonsanddebugerss.controllers.web;

import org.example.dungeonsanddebugerss.model.entities.CityEntity;
import org.example.dungeonsanddebugerss.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/web/city")
public class CityWebController {


    private final CityService cityService;

    @Autowired
    public CityWebController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/")
    public String getCities(Model model) {
        model.addAttribute("cities", get10Cities(cityService.getAllCities()));
        return "cities";
    }


    private List<CityEntity> get10Cities(List<CityEntity> cities) {
        return cities.subList(0, 10);
    }
}

