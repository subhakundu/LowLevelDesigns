package com.examples.lld.parkinglot.models;

import com.examples.lld.parkinglot.constants.VehicleType;

public class Vehicle {
    private String vehicleNumber;
    private VehicleType vehicleType;

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public Vehicle(String vehicleNumber, VehicleType vehicleType) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
}
