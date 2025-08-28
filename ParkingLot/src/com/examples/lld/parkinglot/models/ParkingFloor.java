package com.examples.lld.parkinglot.models;

import com.examples.lld.parkinglot.constants.VehicleType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ParkingFloor {
    private int floorNumber;
    private Map<VehicleType, List<ParkingSlot>> queueOfAvailableParkingSlot;
    private ReentrantLock reentrantLock = new ReentrantLock();

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public void setQueueOfAvailableParkingSlot(Map<VehicleType, List<ParkingSlot>> queueOfAvailableParkingSlot) {
        this.queueOfAvailableParkingSlot = queueOfAvailableParkingSlot;
    }

    public ParkingSlot assignAvailableParkingSlot(VehicleType vehicleType, Ticket ticket) {
        reentrantLock.lock();
        try {
            if (queueOfAvailableParkingSlot.size() == 0) {
                return null;
            }
            ParkingSlot parkingSlot = queueOfAvailableParkingSlot.get(vehicleType).remove(0);
            parkingSlot.setTicket(ticket);
            parkingSlot.setOccupied(true);
            ticket.setParkingSlot(parkingSlot);
            return parkingSlot;
        } finally {
            reentrantLock.unlock();
        }
    }

    public boolean freeUpAParkingLot(ParkingSlot parkingSlot) {
        reentrantLock.lock();
        try {
           if (parkingSlot.isOccupied() || parkingSlot.getTicket() != null) {
               System.out.println("Parking lot is occupied. Cannot put back in free list.");
               return false;
           }
           List<ParkingSlot> availableParkingSlot = queueOfAvailableParkingSlot.get(VehicleType.SMALL_FOUR_WHEELER_VEHICLE);
           availableParkingSlot.add(parkingSlot);
           queueOfAvailableParkingSlot.put(VehicleType.SMALL_FOUR_WHEELER_VEHICLE, availableParkingSlot);
           return true;
        } finally {
            reentrantLock.unlock();
        }
    }
}
