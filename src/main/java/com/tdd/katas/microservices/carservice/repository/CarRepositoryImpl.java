package com.tdd.katas.microservices.carservice.repository;

import com.tdd.katas.microservices.carservice.model.CarData;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CarRepositoryImpl implements CarRepository {

    private Map<String,CarData> map = new HashMap<>();

    @Override
    public CarData getCarData(String vin) {
        return map.get(vin);
    }

    @Override
    public void createCarData(String vin, CarData carData) throws IllegalStateException {
        if (map.containsKey(vin)) {
            throw new IllegalStateException("Repository already contains a CarData with VIN: ["+vin+"]");
        }
        map.put(vin, carData);
    }

    @Override
    public void deleteAllCarData() {
        map.clear();
    }

}
