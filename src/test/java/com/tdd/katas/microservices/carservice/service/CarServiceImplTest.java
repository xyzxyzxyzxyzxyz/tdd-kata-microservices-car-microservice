package com.tdd.katas.microservices.carservice.service;

import com.tdd.katas.microservices.carservice.model.CarData;
import com.tdd.katas.microservices.carservice.repository.CarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CarServiceImpl.class)
public class CarServiceImplTest {
    @Autowired
    private CarService carService;

    @MockBean
    private CarRepository carRepository;

    @Test
    public void The_service_delegates_the_calls_to_the_repository() {
        String VIN_CODE = "XXX";

        CarData expectedCarData = new CarData("W111","Seat Leon","Red");

        given(carRepository.getCarData(VIN_CODE)).willReturn(expectedCarData);

        CarData actualCarData = carService.getCarData(VIN_CODE);

        // The service must delegate the call to the repository with the same input
        verify(carRepository).getCarData(VIN_CODE);

        assertEquals("The service should return the CarData as provided by the repository", expectedCarData, actualCarData);
    }

    @Test
    public void The_service_propagates_the_errors_from_the_repository() {

        given(carRepository.getCarData(any())).willThrow(new IllegalStateException("database not ready"));

        try {
            carRepository.getCarData(any());
            fail("Should have thrown an exception");
        } catch (IllegalStateException e) {
            // The error has been propagated by the service
        }

        // The service must delegate the call to the repository with the same input
        verify(carRepository).getCarData(any());


    }

}
