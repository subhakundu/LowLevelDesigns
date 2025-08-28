package com.examples.lld.parkinglot.billing;

import com.examples.lld.parkinglot.constants.VehicleType;
import com.examples.lld.parkinglot.models.Ticket;
import com.examples.lld.parkinglot.models.Vehicle;

/**
 * Has to be singletone.
 */
public class HourlyBilling implements BillingUtility {
    private static HourlyBilling hourlyBilling;
    private long HOURLY_RATE = 50;
    private HourlyBilling() {
    }

    public static HourlyBilling getHourlyBilling() {
        if (hourlyBilling == null) {
            hourlyBilling = new HourlyBilling();
        }
        return hourlyBilling;
    }

    @Override
    public double calculateBill(Ticket ticket) {
        long startTime = ticket.getStartTime();
        long endTime = ticket.getEndTime();
        long durationInSeconds = endTime - startTime;
        Vehicle vehicle = ticket.getVehicle();
        VehicleType vehicleType = vehicle.getVehicleType();
        return (vehicleType.getBaseCharge() + (durationInSeconds/3600) * vehicleType.getChargePerHour());
    }
}
