package com.example.demo;

import com.example.demo.domain.City;
import com.example.demo.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements ApplicationRunner {

    @Autowired
    private CityMapper cityMapper;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    public void run(ApplicationArguments args) throws Exception {
        City city = this.cityMapper.findByState("CA");
        System.out.println(city);
    }
}
