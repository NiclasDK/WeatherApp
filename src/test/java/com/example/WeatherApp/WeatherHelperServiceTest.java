package com.example.WeatherApp;

import com.example.WeatherApp.Controller.WeatherApiController;
import com.example.WeatherApp.Service.WeatherHelperService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class WeatherHelperServiceTest {

	@Mock
	WeatherApiController weatherApiController = new WeatherApiController();
	WeatherHelperService weatherHelperService = new WeatherHelperService();

	@Test
	void testRequest2Cities_2ObjectsReturned() throws JSONException, IOException {
		ArrayList<String> citynames = new ArrayList<>();
		citynames.add("London");
		citynames.add("Copenhagen");
		Mockito.when(weatherApiController.getDataFromMultipleCityNames(citynames, "metric")).thenReturn(new String("Something about London and something about Copenhagen"));
		String returnedJsonObjects = weatherApiController.getDataFromMultipleCityNames(citynames, "metric");
		Assert.state(returnedJsonObjects.contains("London") && returnedJsonObjects.contains("Copenhagen"), "The returned data does not contain the data for the searched cities");
	}
	@Test
	void testGetDataForNext5DaysForSingleLocation() throws JSONException, IOException {
		Mockito.when(weatherApiController.getDataBasedOnCityName(any(), any())).thenReturn(new JSONObject());
		JSONObject dataBasedOnCityName = weatherApiController.getDataBasedOnCityName("Doha", "metric");
		System.out.println(weatherHelperService.getFiveDayForecastForSingularLocation( dataBasedOnCityName));
		Assert.isInstanceOf(new ArrayList<JSONObject>().getClass(),weatherHelperService.getFiveDayForecastForSingularLocation(dataBasedOnCityName));
		Assert.state(weatherHelperService.getFiveDayForecastForSingularLocation(dataBasedOnCityName)!=null,"list is empty");
	}

	@Test
	void testGetLocationsFromListAboveGivenTemp() throws JSONException, IOException {
		ArrayList<String> cityNames = new ArrayList<>();
		cityNames.add("Copenhagen");
		cityNames.add("Doha");
		//ArrayList<JSONObject> dataBasedOnCityName = weatherApiController.getDataFromMultipleCityNames(cityNames, "metric");
		//System.out.println(weatherHelperService.filterListByTemperature(15.0,dataBasedOnCityName));
	}




}
