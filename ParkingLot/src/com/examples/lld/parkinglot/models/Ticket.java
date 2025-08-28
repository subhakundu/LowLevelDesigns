package com.examples.lld.parkinglot.models;

import com.examples.lld.parkinglot.constants.BillingType;

public class Ticket {
    private Vehicle vehicle;
    private long startTime;
    private long endTime;
    private BillingType billingType;
    private ParkingSlot parkingSlot;

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public long getStartTime() {
        return startTime;
    }

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public void setParkingSlot(ParkingSlot parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    public Ticket(Vehicle vehicle, long startTime) {
        this.vehicle = vehicle;
        this.startTime = startTime;
    }

    public BillingType getBillingType() {
        return billingType;
    }

    public void setBillingType(BillingType billingType) {
        this.billingType = billingType;
    }
}
