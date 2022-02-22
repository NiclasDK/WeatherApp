package com.example.WeatherApp.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WeatherHelperService {

    public static final String APP_KEY ="d422356ec63c259053280e36dbe53c54";

    public ArrayList<JSONObject> filterListByTemperature(Double expectedTemperature, ArrayList<JSONObject> locationsToFilter) throws JSONException {
        ArrayList<JSONObject> locationsToReturn = (ArrayList<JSONObject>) locationsToFilter.stream().filter(l -> {
            try {
                return nextDaysTempForSingularLocation(l)>expectedTemperature;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        });

        return locationsToReturn;
    }

    public ArrayList<JSONObject> getFiveDayForecastForSingularLocation(JSONObject location) throws JSONException {
        ArrayList<JSONObject> temperatures = new ArrayList<>();

        System.out.println(location);

        JSONObject subjects = location;

        for (int i = 0; i < subjects.length();i++) {
            JSONObject jsonObject = subjects.getJSONObject(String.valueOf(i));
            JSONObject jsonMainDataObject = (JSONObject) jsonObject.get("main");
            JSONObject item = new JSONObject();
            item.put("date",jsonObject.get("dt_txt"));
            item.put("temp",jsonMainDataObject.get("temp"));
            temperatures.add(item);
        }
        return temperatures;
    }

    private Double nextDaysTempForSingularLocation(JSONObject location) throws JSONException {
        JSONArray subjects = (JSONArray)location.get("list");
        //index 2 is the next day
        JSONObject nextDayJSON = subjects.getJSONObject(2);
        JSONObject main = (JSONObject) nextDayJSON.get("main");
        Double tempInDouble = Double.parseDouble(main.get("temp").toString());
        return tempInDouble;
    }
}
