package com.tdd.katas.microservices.carservice.repository;

import com.tdd.katas.microservices.carservice.model.CarData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CarRepositoryImpl.class)
public class CarRepositoryImplTest
{

    @Autowired
    private CarRepositoryImpl carRepository;

    @Before
    public void setUp() {
        // Clear repository between tests
        carRepository.deleteAllCarData();
    }

    @Test
    public void The_repository_returns_valid_output_for_valid_input() throws Exception {
        String VIN = "XXX";

        CarData expectedCarData = new CarData("W111","Seat Leon","Red");

        carRepository.createCarData(VIN, expectedCarData);

        CarData actualCarData =  carRepository.getCarData(VIN);

        assertEquals("Should return the stored car data", expectedCarData, actualCarData);
    }

    @Test
    public void The_repository_returns_null_for_invalid_input() throws Exception {
        String EXISTING_VIN = "XXX";
        String NON_EXISTING_VIN = "YYY";

        CarData expectedCarData = new CarData("W111","Seat Leon","Red");
        carRepository.createCarData(EXISTING_VIN, expectedCarData);

        CarData actualCarData =  carRepository.getCarData(NON_EXISTING_VIN);

        assertNull("The car data should not exist in the database", actualCarData);
    }

    @Test
    public void The_repository_accepts_creating_a_non_existing_vin() throws Exception {
        final String VIN = "X";

        CarData expectedCarData = new CarData("PLATENUMBER_X", "MODEL_X", "COLOR_X");

        carRepository.createCarData(VIN, expectedCarData);

        CarData actualCarData =  carRepository.getCarData(VIN);

        assertEquals("The car data should exist in the repository after creation", expectedCarData, actualCarData);
    }

    @Test
    public void The_repository_does_not_accept_creating_an_already_existing_vin() throws Exception {
        final String VIN = "X";

        CarData expectedCarData = new CarData("PLATENUMBER_X", "MODEL_X", "COLOR_X");

        carRepository.createCarData(VIN, expectedCarData);

        CarData actualCarData =  carRepository.getCarData(VIN);

        assertEquals("The car data should exist in the repository after creation", expectedCarData, actualCarData);

        try {
            carRepository.createCarData(VIN, expectedCarData);
            fail("Should not have accepted the creation of an already existing VIN");
        }
        catch (IllegalStateException e) {
            // Ok
        }

    }

    @Test
    public void The_repository_allows_deleting_all_the_data() throws Exception {
        final String VIN_X = "VIN_X";
        final String VIN_Y = "VIN_Y";

        carRepository.createCarData(VIN_X, new CarData("PLATENUMBER_X", "MODEL_X", "COLOR_X"));
        carRepository.createCarData(VIN_Y, new CarData("PLATENUMBER_Y", "MODEL_Y", "COLOR_Y"));

        assertNotNull("The car data should exist after creation", carRepository.getCarData(VIN_X));
        assertNotNull("The car data should exist after creation", carRepository.getCarData(VIN_Y));

        carRepository.deleteAllCarData();

        assertNull("The car data should not exist after clearing the repository", carRepository.getCarData(VIN_X));
        assertNull("The car data should not exist after clearing the repository", carRepository.getCarData(VIN_Y));
    }


}
