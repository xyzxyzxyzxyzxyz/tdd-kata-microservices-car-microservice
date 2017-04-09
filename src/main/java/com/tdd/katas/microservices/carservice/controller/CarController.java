package com.tdd.katas.microservices.carservice.controller;

import com.tdd.katas.microservices.carservice.model.CarData;
import com.tdd.katas.microservices.carservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/{vinCode}")
    public ResponseEntity<CarData> getCarData(@PathVariable String vinCode) {
        CarData carData;

        try {
            carData = carService.getCarData(vinCode);
        } catch(Throwable error) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (carData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(carData, HttpStatus.OK);
    }

}
