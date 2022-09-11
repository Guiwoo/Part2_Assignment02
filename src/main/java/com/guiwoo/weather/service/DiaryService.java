package com.guiwoo.weather.service;

import com.guiwoo.weather.Part2Assignment02Application;
import com.guiwoo.weather.domain.DateWeather;
import com.guiwoo.weather.domain.Diary;
import com.guiwoo.weather.error.DateErrorCode;
import com.guiwoo.weather.error.DateException;
import com.guiwoo.weather.repository.DateWeatherRepository;
import com.guiwoo.weather.repository.DiaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.guiwoo.weather.error.DateErrorCode.DATE_FUTURE_ERROR;
import static com.guiwoo.weather.error.DateErrorCode.DATE_PAST_ERROR;

@Service
@Slf4j
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final DateWeatherRepository weatherRepository;
    @Value("${openweather.key}")
    private String apiKey;
    private static final Logger logger = LoggerFactory.getLogger(Part2Assignment02Application.class);

    public DiaryService(DiaryRepository diaryRepository,DateWeatherRepository dt) {
        this.diaryRepository = diaryRepository;
        this.weatherRepository = dt;
    }
    @Transactional
    @Scheduled(cron = "0 0 1 * * *")
    public void saveDateWeather(){
        weatherRepository.save(getWeatherFromApi());
    }

    private DateWeather getWeatherFromApi(){
        //open weather api 에서 가져오기
        String data = getWeatherToString();
        // 받아온 데이터 파싱
        Map<String,Object> parsedData = parseWeather(data);
        DateWeather d = new DateWeather();
        d.setDate(LocalDate.now());
        d.setWeather(parsedData.get("main").toString());
        d.setIcon(parsedData.get("icon").toString());
        d.setTemperature((double) parsedData.get("temp"));
        return d;
    }
    public void createDiary(LocalDate date, String text) {
        logger.info("Started to created diary");
        validDateCheck(date);
        //db 에서가져오기 vs api 에서 가져오기
        DateWeather d = getDateWeather(date);

        // 파싱된 데이터 일기 값 디비에 넣기
        Diary diary = new Diary();
        diary.setDateWeather(d);
        diary.setText(text);

        diaryRepository.save(diary);
        logger.info("End to created diary");
    }

    private DateWeather getDateWeather(LocalDate date) {
        validDateCheck(date);
        List<DateWeather> lb= weatherRepository.findAllByDate(date);
        if(lb.size() == 0){
            //api에서 새로 가져 와야 하는데 현재 날씨를 가져오도록
            return getWeatherFromApi();
        }else{
            return lb.get(0);
        }
    }

    @Transactional(readOnly = true)
    public List<Diary> readDiary(LocalDate date){
        logger.debug("Debuting diary");
        validDateCheck(date);
        return diaryRepository.findAllByDate(date);
    }
    @Transactional(readOnly = true)
    public List<Diary> readDiaries(LocalDate start,LocalDate end){
        validDateCheck(start);
        validDateCheck(end);
        return diaryRepository.findAllByDateBetween(start,end);
    }
    public void deleteDiary(LocalDate date){
        diaryRepository.deleteAllByDate(date);
    }

    public void updateDiary(LocalDate date,String text){
        validDateCheck(date);
        Diary nD = diaryRepository.getFirstByDate(date).orElseThrow(()-> new RuntimeException("없어요"));
        nD.setText(text);
    }
    private String getWeatherToString() {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid="+apiKey;
        try{
            URL base = new URL(url);
            HttpURLConnection con = (HttpURLConnection) base.openConnection();
            con.setRequestMethod("GET");
            int rsCode = con.getResponseCode();
            BufferedReader bf;
            if(rsCode == 200 ){
                bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }else{
                bf = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while((inputLine=bf.readLine())!= null){
                response.append(inputLine);
            }
            bf.close();
            return response.toString();
        }catch (IOException e){
            System.out.println(e);
            log.error("failed");
            return "Failed to get a data from api";
        }
    }
    private Map<String,Object> parseWeather(String data){
        JSONParser j = new JSONParser();
        JSONObject jsonObject;
        try{
            jsonObject = (JSONObject) j.parse(data);
        } catch (ParseException e) {
            log.error("Parsing Error : {}",e);
            throw new RuntimeException(e);
        }
        Map<String,Object> map = new HashMap<>();
        JSONObject mainData = (JSONObject) jsonObject.get("main");
        map.put("temp",mainData.get("temp"));
        JSONArray weatherData = (JSONArray) jsonObject.get("weather");
        JSONObject obj = (JSONObject) weatherData.get(0);
        map.put("main",obj.get("main"));
        map.put("icon",obj.get("icon"));

        return map;
    }

    private void validDateCheck(LocalDate date){
        if(date.isAfter(LocalDate.now())){
            log.error(DATE_FUTURE_ERROR.getHttpStatus().value() +" "+ DATE_FUTURE_ERROR.getMessage());
            throw new DateException(DATE_FUTURE_ERROR);
        }
        if(date.isBefore(LocalDate.ofYearDay(1990,1))){
            log.error(DATE_PAST_ERROR.getHttpStatus().value() +" "+ DATE_PAST_ERROR.getMessage());
            throw new DateException(DATE_PAST_ERROR);
        }
    }
}
