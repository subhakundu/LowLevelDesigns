package com.examples.lld.parkinglot.constants;

public enum VehicleType {
    SMALL_FOUR_WHEELER_VEHICLE(10.00, 5.00);

    private double baseCharge;
    private double chargePerHour;

    public double getBaseCharge() {
        return baseCharge;
    }

    public void setBaseCharge(double baseCharge) {
        this.baseCharge = baseCharge;
    }

    public double getChargePerHour() {
        return chargePerHour;
    }

    public void setChargePerHour(double chargePerHour) {
        this.chargePerHour = chargePerHour;
    }

    VehicleType(double _baseCharge, double _chargePerHour) {
        baseCharge = _baseCharge;
        chargePerHour = _chargePerHour;
    }
}
