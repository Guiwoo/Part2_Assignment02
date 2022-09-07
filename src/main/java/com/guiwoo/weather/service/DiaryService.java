package com.guiwoo.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DiaryService {
    @Value("$openweather.key")
    private String apiKey;
    public void createDiary(LocalDate date, String text) {

    }
    private String getWeatherToString(){
        //https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=81940a2c92cc8e642204fb18780bff4b
        String api = "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid="+apiKey;
    }
}
