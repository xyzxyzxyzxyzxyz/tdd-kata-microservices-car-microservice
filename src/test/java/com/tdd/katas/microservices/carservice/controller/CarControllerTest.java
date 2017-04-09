package com.tdd.katas.microservices.carservice.controller;

import com.tdd.katas.microservices.carservice.model.CarData;
import com.tdd.katas.microservices.carservice.service.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CarController.class)
@WithMockUser
public class CarControllerTest {
    @Autowired
    private CarController carController;

    @MockBean
    private CarService carService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void Can_create_instance() {
        assertNotNull("Should be able to create instance", carController);
    }

    @Test
    public void It_returns_valid_data_for_a_valid_input() throws Exception {

        final String VIN_CODE = "XXX";

        CarData expectedCarData = new CarData("W111","Seat Leon","Red");

        given(carService.getCarData(VIN_CODE)).willReturn(expectedCarData);

        this.mvc.perform(
                    get("/cars/" + VIN_CODE)
                    .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.plateNumber", is(expectedCarData.getPlateNumber())))
                .andExpect(jsonPath("$.model", is(expectedCarData.getModel())))
                .andExpect(jsonPath("$.color", is(expectedCarData.getColor())));

        verify(carService).getCarData(VIN_CODE);

    }

    @Test
    public void It_returns_404_if_the_customerId_does_not_exist() throws Exception {

        given(carService.getCarData(any())).willReturn(null);

        this.mvc
                .perform(
                        get("/cars/" + "PEPITO")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());

        verify(carService).getCarData(any());

    }

    @Test
    public void It_returns_500_if_the_service_throws_an_error() throws Exception {

        final String VIN_CODE = "XXX";

        given(carService.getCarData(any())).willThrow(
                new IllegalStateException("service is not ready"));

        this.mvc
                .perform(
                        get("/cars/" + VIN_CODE)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError());

        verify(carService).getCarData(any());

    }

}
