package edu.arapahoe.csc245;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;
import com.google.gson.reflect.*;

public class CSC245_Project3_secure {

    // Java Maps are used with many API interactions. OpenWeatherMap also uses Java Maps.
    public static Map<String, Object> jsonToMap(String str) {
        Map<String, Object> map = new Gson().fromJson(
                str, new TypeToken<HashMap<String, Object>>() {
                }.getType()
        );
        return map;
    }

    public static String getTempForCity(String cityString, String api_key) {
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" +
                cityString + "&appid=" + api_key + "&units=imperial";
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
           //String line; Removing this string line because it is never used helps with keeping code clean since its never called Issue 1
            // while ((line = rd.readLine()) != null){     Removing that while to get rid of garbage collector to null helps with Issue 6
            //}
            result .append(rd.readLine());
            System.out.println(result);


            Map<String, Object> respMap = jsonToMap(result.toString());
            Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());

            return mainMap.get("temp").toString();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "Temp not available (API problem?)";
        }

    }

    public static void main(String[] args) {
        String API_KEY = "" ;      // Changing name to API_KEY to make it less confusing Issue 3
        String LOCATION = "Castle Rock, US"; //Making Location into its own String separated from the API_KEY Issue 2
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION +
                "&appid=" + API_KEY + "&units=imperial";

        // The following line is out of scope for mitigation and can be ignored.
//		System.out.println("URL invoked to get temperature data=" + urlString);

       // for (int i = 0; i < 10; i++) { //Issue 4/5 Adding a brace and removing semicolon right after a for statement

            System.out.println("Current temperature in " + LOCATION + " is: "
                    + getTempForCity(LOCATION, API_KEY) + " degrees.");


            //urlString = ""; gets rid of unessary code 1

            Runtime.getRuntime().exit(1);//This helps Issue 7 FIO14-J making an runtime exit so after it prints it exits.
       // }
    }
}



