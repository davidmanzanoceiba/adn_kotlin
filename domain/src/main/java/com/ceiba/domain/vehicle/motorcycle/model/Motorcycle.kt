package com.ceiba.domain.vehicle.motorcycle.model

import com.ceiba.domain.vehicle.model.Vehicle
import java.time.LocalDateTime

class Motorcycle(licensePlate: String, entryDate: LocalDateTime, cylinderCapacity : String) :
    Vehicle(licensePlate, entryDate) {
    override fun saveVehicle() {
        TODO("Not yet implemented")
    }

    override fun parkingCost(): Int {
        TODO("Not yet implemented")
    }
}