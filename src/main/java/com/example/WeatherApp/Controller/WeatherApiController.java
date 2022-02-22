package com.example.WeatherApp.Controller;

import com.example.WeatherApp.Service.WeatherHelperService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

@RestController
public class WeatherApiController {

    WeatherHelperService weatherHelperService = new WeatherHelperService();

    @GetMapping("/weatherAPI/{cityName}/{units}")
    public JSONObject getDataBasedOnCityName(@PathVariable String cityName, @PathVariable String units) throws JSONException, IOException {
        String urlRequest = "http://api.openweathermap.org/data/2.5/forecast?q="+cityName+"&units="+units+"&appid="+ WeatherHelperService.APP_KEY;
        JSONObject cityData = getJsonObject(urlRequest);
        return cityData;
    }

    @GetMapping("/weatherAPI/locations/{cityNames}/{units}")
    public String getDataFromMultipleCityNames(@PathVariable ArrayList<String> cityNames,@PathVariable String units) throws JSONException, IOException {
        ArrayList<JSONObject> listOfCities = new ArrayList<>();
        cityNames.stream().forEach(cityName -> {
            String urlRequest = "http://api.openweathermap.org/data/2.5/forecast?q="+cityName+"&units="+units+"&appid="+ WeatherHelperService.APP_KEY;
            JSONObject jsonObj = null;
                try {
                    jsonObj = getJsonObject(urlRequest);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listOfCities.add(jsonObj);
        }
        );
        return listOfCities.toString();
    }

    @GetMapping("/weatherAPI/locations/{cityNames}/{units}/{expectedTemperature}")
    public String getFilteredDataFromMultipleCityNames(@PathVariable ArrayList<String> cityNames,@PathVariable String units, @PathVariable Double expectedTemperature) throws JSONException, IOException {
        ArrayList<JSONObject> listOfCities = new ArrayList<>();

        cityNames.stream().forEach(cityName -> {
        String urlRequest = "http://api.openweathermap.org/data/2.5/forecast?q="+cityName+"&units="+units+"&appid="+ WeatherHelperService.APP_KEY;
            JSONObject jsonObj = null;
            try {
                jsonObj = getJsonObject(urlRequest);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listOfCities.add(jsonObj);
        });

        ArrayList<JSONObject> filteredList;
        filteredList = weatherHelperService.filterListByTemperature(expectedTemperature, listOfCities);
        return filteredList.toString();
    }

    @GetMapping("/weatherAPI/forecast/{cityName}/{units}")
    public String getForecastDataBasedOnCityName(@PathVariable String cityName,@PathVariable String units) throws JSONException, IOException {
        String urlRequest = "http://api.openweathermap.org/data/2.5/forecast?q="+cityName+"&units="+units+"&appid="+ WeatherHelperService.APP_KEY;
        JSONObject jsonObj = getJsonObject(urlRequest);
        ArrayList<JSONObject> fiveDayForecastForSingularLocation = weatherHelperService.getFiveDayForecastForSingularLocation(jsonObj);
        return fiveDayForecastForSingularLocation.toString();
    }

    private JSONObject getJsonObject(String urlRequest) throws IOException, JSONException {
        URL url = new URL(urlRequest);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String aPIResponse;
        StringBuilder jsonString = new StringBuilder();
        while((aPIResponse = br.readLine()) != null){
            jsonString.append(aPIResponse);
        }
        JSONObject jsonObj = new JSONObject(jsonString.toString());
        return jsonObj;
    }

}
