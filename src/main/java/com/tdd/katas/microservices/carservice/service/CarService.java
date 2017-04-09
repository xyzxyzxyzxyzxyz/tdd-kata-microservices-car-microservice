package com.tdd.katas.microservices.carservice.service;

import com.tdd.katas.microservices.carservice.model.CarData;

public interface CarService {
    CarData getCarData(String vin);
}
