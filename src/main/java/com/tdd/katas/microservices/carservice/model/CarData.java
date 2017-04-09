package com.tdd.katas.microservices.carservice.model;

public class CarData {
    private String plateNumber;
    private String model;
    private String color;


    public CarData(String plateNumber, String model, String color) {
        this.plateNumber = plateNumber;
        this.model = model;
        this.color = color;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        return
                obj != null
                && obj instanceof CarData
                && this.plateNumber != null
                && this.plateNumber.equals(((CarData) obj).getPlateNumber());
    }

}
