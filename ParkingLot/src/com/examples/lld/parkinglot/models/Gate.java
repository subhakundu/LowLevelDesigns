package com.examples.lld.parkinglot.models;

import com.examples.lld.parkinglot.constants.GateType;

import java.util.List;

public class Gate {
    private int gateNumber;
    private GateType gateType;
    /**
     * Significance of this will be different for entry and exit gates.
     * For entry gate: slot has to be assigned.
     * For exit gate: billing has to be calculated.
     */
    private List<Vehicle> listOfWaitingVehicles;

    public int getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(int gateNumber) {
        this.gateNumber = gateNumber;
    }

    public GateType getGateType() {
        return gateType;
    }

    public void setGateType(GateType gateType) {
        this.gateType = gateType;
    }

    public List<Vehicle> getListOfWaitingVehicles() {
        return listOfWaitingVehicles;
    }

    public void setListOfWaitingVehicles(List<Vehicle> listOfWaitingVehicles) {
        this.listOfWaitingVehicles = listOfWaitingVehicles;
    }

    public Gate(int gateNumber, GateType gateType, List<Vehicle> listOfWaitingVehicles) {
        this.gateNumber = gateNumber;
        this.gateType = gateType;
        this.listOfWaitingVehicles = listOfWaitingVehicles;
    }
}
