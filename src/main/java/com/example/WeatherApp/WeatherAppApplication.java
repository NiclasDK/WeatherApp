package com.example.WeatherApp;

import com.example.WeatherApp.Controller.WeatherApiController;
import com.example.WeatherApp.Service.WeatherHelperService;
import net.minidev.json.parser.ParseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootApplication
public class WeatherAppApplication {

	public static void main(String[] args) throws IOException, JSONException, ParseException {
		SpringApplication.run(WeatherAppApplication.class, args);
	}

}
