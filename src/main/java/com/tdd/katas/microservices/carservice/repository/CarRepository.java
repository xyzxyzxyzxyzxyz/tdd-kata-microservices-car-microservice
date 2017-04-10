package com.tdd.katas.microservices.carservice.repository;


import com.tdd.katas.microservices.carservice.model.CarData;

public interface CarRepository {
    CarData getCarData(String vin);

    void createCarData(String vin, CarData carData) throws IllegalStateException ;

    void deleteAllCarData();
}
