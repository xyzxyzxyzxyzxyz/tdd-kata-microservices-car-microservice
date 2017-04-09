package com.tdd.katas.microservices.carservice.service;

import com.tdd.katas.microservices.carservice.model.CarData;
import com.tdd.katas.microservices.carservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public CarData getCarData(String vin) {
        return carRepository.getCarData(vin);
    }

}
