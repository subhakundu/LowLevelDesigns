package com.examples.lld.parkinglot.models;

import com.examples.lld.parkinglot.constants.VehicleType;

public class ParkingSlot {
    private ParkingFloor parkingFloor;
    private VehicleType vehicleType;
    private boolean isOccupied;
    private String slotId;
    private Ticket ticket;

    public synchronized boolean cleanUpParkingLot() {
        if (!isOccupied || ticket == null) {
            return false;
        }
        isOccupied = false;
        ticket = null;
        parkingFloor.freeUpAParkingLot(this);
        return true;
    }

    public void setParkingFloor(ParkingFloor parkingFloor) {
        this.parkingFloor = parkingFloor;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public ParkingSlot(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
        this.isOccupied = false;
        this.slotId = "";
    }
}
