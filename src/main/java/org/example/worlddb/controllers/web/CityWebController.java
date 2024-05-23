package org.example.worlddb.controllers.web;

import org.example.worlddb.model.entities.CityEntity;
import org.example.worlddb.model.repositories.CityEntityRepository;
import org.example.worlddb.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/cities")
public class CityWebController {


    private final CityService cityService;
    private final CityEntityRepository cityEntityRepository;

    @Autowired
    public CityWebController(CityService cityService,
                             CityEntityRepository cityEntityRepository) {
        this.cityService = cityService;
        this.cityEntityRepository = cityEntityRepository;
    }

    @GetMapping("/")
    public String getCities(Model model) {
        model.addAttribute("cities", get10Cities(cityService.getAllCities()));
        return "cities";
    }

    //
    @GetMapping("/header")
    public String getHeaders(Model model) {
        return "index";
    }


    private List<CityEntity> get10Cities(List<CityEntity> cities) {
        return cities.subList(0, 10);
    }

    @GetMapping("/add-city")
    public String addCity(Model model) {
        CityEntityRepository cityEntityRepository1 = cityEntityRepository;
        model.addAttribute("city", cityEntityRepository1);
        return "add-city";
    }
    /* @GetMapping("/save-city")
    public String saveCity(Model model) {}*/

    @GetMapping("/delete/{id}")
    public String deleteCityById(@PathVariable Integer id, Model model) {
    cityService.getCityById(id).ifPresent(cityToDelete->cityService.deleteCity(id));
        return "redirect:/web/cities";
        }


    @GetMapping("/update/{id}")
    public String editCityById(@PathVariable Integer id, Model model) {
        CityEntity cityToUpdate = cityService.getCityById(id).orElse(null);
        model.addAttribute("city", cityToUpdate);
        model.addAttribute("id", id);
        return "update-city";
    }

    @PostMapping("/update-city/{id}")
    public String updateCity(@PathVariable Integer id, @ModelAttribute("city")  CityService cityService) {
    CityEntity cityToUpdate = cityService.getCityById(id).orElse(null);
    return "redirect:/web/cities";
    }



}

