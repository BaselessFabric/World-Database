package org.example.dungeonsanddebugerss.service;

import org.example.dungeonsanddebugerss.model.entities.CityEntity;
import org.example.dungeonsanddebugerss.model.entities.CountryEntity;

import org.example.dungeonsanddebugerss.model.entities.CountryLanguageEntity;
import org.example.dungeonsanddebugerss.model.respositories.CityEntityRepository;
import org.example.dungeonsanddebugerss.model.respositories.CountryEntityRepository;
import org.example.dungeonsanddebugerss.model.respositories.CountryLanguageEntityRepository;


import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;


@Service
public class WorldService {

    private final Logger logger = Logger.getLogger("Spring Logger");
    private final CountryService countryService;

    private CityEntityRepository cityEntityRepository;
    private CountryEntityRepository countryEntityRepository;
    private CountryLanguageEntityRepository countrylanguageEntityRepository;

    public WorldService(CityEntityRepository cityEntityRepository, CountryEntityRepository countryEntityRepository, CountryLanguageEntityRepository countrylanguageEntityRepository, CountryService countryService) {
        this.cityEntityRepository = cityEntityRepository;
        this.countryEntityRepository = countryEntityRepository;
        this.countrylanguageEntityRepository = countrylanguageEntityRepository;
        this.countryService = countryService;
    }

    public float findPercentageOfPopulationInLargestCity(String countryName) {
        int countryPopTotal = 0;
        String countryCode = "";

        //for each country
        for(CountryEntity c : countryEntityRepository.findAll())
        {
            if(countryName.equals(c.getName()))
            {
                countryPopTotal = c.getPopulation();
                logger.info("Country: " + c.getName() + " | Population: " + countryPopTotal);
                countryCode = c.getCode();
            }
        }

        if(!countryCode.isEmpty())
        {
            int highestPop = 0;
            String highestPopCityName = "";
            for(CityEntity c : cityEntityRepository.findAll())
            {
                if(countryCode.equals(c.getCountryCode().getCode()))
                {
                    if(highestPop < c.getPopulation())
                    {
                        highestPop = c.getPopulation();
                        highestPopCityName = c.getName();
                    }
                }
            }
            float result = (float) highestPop / countryPopTotal;
            result *= 100;
            logger.info("Highest city pop: " + highestPopCityName + " | Population: " + highestPop);
            logger.info("Percentage: " + result);
            return result;
        }
        return 0;
    }

    public List<CityEntity> find5SmallestDistrictsOfCity(String city) {
        List<CityEntity> cityEntities = cityEntityRepository.findAll();
        ArrayList<CityEntity> lowest = new ArrayList<>();

        for(CityEntity cityEntity : cityEntities) {
            if(cityEntity.getDistrict().equals(city)){
                lowest.add(cityEntity);
            }
        }


        lowest.sort(Comparator.comparing(CityEntity::getPopulation));

        List<CityEntity> top5Cities = lowest.subList(0, Math.min(5, lowest.size()));

        for (int i = 0; i < top5Cities.size(); i++) {
            CityEntity cities = top5Cities.get(i);
            logger.info("City " + (i+1) + ": " + cities.getName() + ", Population: " + cities.getPopulation());
        }

        return top5Cities;
    }

    public List<CountryEntity> findCountryWithMostCity(){
        Map<String, Integer> countryCityMap = new HashMap<>();
        for(CityEntity city : cityEntityRepository.findAll()){
            String countryCode = city.getCountryCode().getCode();
            countryCityMap.put(countryCode,countryCityMap.getOrDefault(countryCode, 0) + 1 );
        }

        int maxCityCount = 0;
        String countryCode = "";
        for(Map.Entry<String, Integer> entry : countryCityMap.entrySet()){
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(value > maxCityCount){
                maxCityCount = value;
                countryCode = key;
            }
        }
        Optional<CountryEntity> country = countryService.getCountryByCode(countryCode);
        List<CountryEntity> countries = new ArrayList<>();
        country.ifPresent(countries::add);
        return countries;
    }

    public int returnNumOfCities(){
        ArrayList<CityEntity>  citiesInCountry = new ArrayList<>();
        List<CountryEntity> countryWithMostCities = findCountryWithMostCity();
        logger.info("Country with most cities is " + countryWithMostCities);
        String countryCode = countryWithMostCities.getFirst().getCode();
        logger.info("Country code for country with most cities is " + countryCode);

         List<CityEntity> cityList = cityEntityRepository.findAll();
          for(CityEntity city: cityList){
              if(city.getCountryCode().getCode().equals(countryCode)){
                  logger.info("City in country with most cities is " + city.getName());
                  citiesInCountry.add(city);

              }

          }

        return citiesInCountry.size();
    }

    public List<CountryEntity> findCountriesWithNoHeadOfState() {
        logger.info("Starting findCountriesWithNoHeadOfState method");
        List<CountryEntity> allCountries = countryEntityRepository.findAll();
        logger.fine("Collected all countries");
        List<CountryEntity> noHeadOfStateCountries = new ArrayList<>();
        logger.fine("Looping through countries to find no head of state");
        for (CountryEntity countryEntity : allCountries) {
            if (countryEntity.getHeadOfState() == null) {
                logger.finer("Found no head of state, adding country to return list");
                noHeadOfStateCountries.add(countryEntity);
            }
        }

        logger.info("Finished findCountriesWithNoHeadOfState method");
        return noHeadOfStateCountries;
    }

    public int findCountOfMostPopularLanguage(String countryName){
            int totalPopulation;
            String countryCode;
        logger.info("Finding the country");
        CountryEntity country = findCountry(countryName);
        if(country != null){
            logger.fine("Country found");
            countryCode = country.getCode();
            totalPopulation = country.getPopulation();
        }else{
            logger.warning("Country not found");
            return -1;
        }
        logger.info("Getting number of people who speaks each official language");
        ArrayList<Integer> peopleWhoSpeakLanguage = new ArrayList<Integer>();
        for(CountryLanguageEntity language : getAllLanguageCountrySpeaks(countryCode)){
            if(language.getIsOfficial().equals("T")){
                peopleWhoSpeakLanguage.add(Math.round(totalPopulation * (language.getPercentage().floatValue()/100)));
            }
        }

        if(peopleWhoSpeakLanguage.isEmpty()){
            logger.info("No official language exists for this country");
            return -1;
        }

        Collections.sort(peopleWhoSpeakLanguage);
        return peopleWhoSpeakLanguage.getLast();
    }

    private CountryEntity findCountry(String countryName){
        for(CountryEntity country : countryEntityRepository.findAll()) {
            if(country.getName().equals(countryName)) {
                return country;
            }
        }
        return null;
    }

    private List<CountryLanguageEntity> getAllLanguageCountrySpeaks(String countryCode){
        List<CountryLanguageEntity> countries = new ArrayList<>();
        for(CountryLanguageEntity countrylanguageEntity : countrylanguageEntityRepository.findAll()){
            if(countrylanguageEntity.getCountryCode().getCode().equals(countryCode)){
                countries.add(countrylanguageEntity);
            }
        }
        return countries;
    }

}
