package com.tdd.katas.microservices.carservice.repository;

import com.tdd.katas.microservices.carservice.model.CarData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CarRepositoryImpl.class)
public class CarRepositoryImplTest
{

    @Autowired
    private CarRepositoryImpl carRepository;

    @Test
    public void The_repository_returns_valid_output_for_valid_input() throws Exception {
        String VIN = "XXX";

        CarData expectedCarData = new CarData("W111","Seat Leon","Red");

        carRepository.store(VIN, expectedCarData);

        CarData actualCarData =  carRepository.getCarData(VIN);

        assertEquals("Should return the stored car data", expectedCarData, actualCarData);
    }

    @Test
    public void The_repository_returns_null_for_invalid_input() throws Exception {
        String EXISTING_VIN = "XXX";
        String NON_EXISTING_VIN = "YYY";

        CarData expectedCarData = new CarData("W111","Seat Leon","Red");
        carRepository.store(EXISTING_VIN, expectedCarData);

        CarData actualCarData =  carRepository.getCarData(NON_EXISTING_VIN);

        assertNull("The car data should not exist in the database", actualCarData);
    }

}
