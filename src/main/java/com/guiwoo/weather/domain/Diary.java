package com.guiwoo.weather.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String weather;
    private String icon;
    private Double temperature;
    private String text;
    private LocalDate date;

    public void setDateWeather(DateWeather d){
        this.date = d.getDate();
        this.icon = d.getIcon();
        this.weather = d.getWeather();
        this.temperature = d.getTemperature();
    }

}
