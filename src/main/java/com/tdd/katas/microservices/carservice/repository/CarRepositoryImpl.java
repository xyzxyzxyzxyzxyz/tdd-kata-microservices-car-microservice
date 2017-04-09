package com.tdd.katas.microservices.carservice.repository;

import com.tdd.katas.microservices.carservice.model.CarData;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CarRepositoryImpl implements CarRepository {

    private Map<String,CarData> map = new HashMap<>();

    void store(String vin, CarData carData) {
        map.put(vin, carData);
    }

    @Override
    public CarData getCarData(String vin) {
        return map.get(vin);
    }

}
