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
//@RequestMapping("/web/cities")
public class CityWebController {


    private final CityService cityService;
    private final CityEntityRepository cityEntityRepository;

    @Autowired
    public CityWebController(CityService cityService,
                             CityEntityRepository cityEntityRepository) {
        this.cityService = cityService;
        this.cityEntityRepository = cityEntityRepository;
    }

    @GetMapping("/web/cities")
    public String getCities(Model model) {
        model.addAttribute("cities", get10Cities(cityService.getAllCities()));
        return "cities";
    }


    private List<CityEntity> get10Cities(List<CityEntity> cities) {
        return cities.subList(0, 10);
    }

    @GetMapping("/web/city/{id}")
    public String getCity(@PathVariable Integer id, Model model) {
        model.addAttribute("city", cityService.getCityById(id).orElse(null));
        return "city";
    }

    @GetMapping("/web/city/add-city")
    public String addCity(Model model) {
        CityEntity newCity = new CityEntity();
        model.addAttribute("city", newCity);
        return "add-city";
    }

     @PostMapping("/web/city/save-city")
    public String saveCity(@ModelAttribute("city") CityEntity city) {
        cityService.createCity(city);
        return "redirect:/web/cities";
     }

    @GetMapping("/web/city/delete/{id}")
    public String deleteCityById(@PathVariable Integer id) {
    cityService.getCityById(id).ifPresent(cityToDelete->cityService.deleteCity(id));
        return "redirect:/web/cities";
        }


    @GetMapping("/web/city/edit/{id}")
    public String editCityById(@PathVariable Integer id, Model model) {
        CityEntity cityToUpdate = cityService.getCityById(id).orElse(null);
        model.addAttribute("city", cityToUpdate);
        return "update-city";
    }

    @PostMapping("/web/city/update/{id}")
    public String updateCity(@ModelAttribute("city") CityEntity city) {
    cityService.updateCity(city.getId(), city);
    return "redirect:/web/cities";
    }

}

