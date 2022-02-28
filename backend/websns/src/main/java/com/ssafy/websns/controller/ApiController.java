package com.ssafy.websns.controller;

import com.ssafy.websns.model.entity.region.Region;
import com.ssafy.websns.repository.region.RegionRepository;
import com.ssafy.websns.weather.Weather;
import com.ssafy.websns.weather.WeatherDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiController {

  private final RegionRepository regionRepository;

  @GetMapping("/weather")
  public ResponseEntity<Weather> weather(@RequestParam(value = "region") String region, @RequestParam(value = "date") String date){

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.M.d.HH:mm");
    LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

    Region regionList = regionRepository.findByRegionName(region);

    Weather weather = null;
    if(regionList != null){
      Region region1 = regionList;
      WeatherDto weatherDto = new WeatherDto(dateTime,region1.getPointCode());
      weather = weatherDto.crawling();
    }
    else{

    }

    return new ResponseEntity<>(weather, HttpStatus.OK);

  }

  @GetMapping("/region")
  public ResponseEntity<List<String>> region(){
    List<Region> regions = regionRepository.findBy();
    List<String> regionNames = regions.stream().map(region -> region.getRegionName())
        .collect(Collectors.toList());

    return new ResponseEntity<>(regionNames,HttpStatus.OK);
  }

}
