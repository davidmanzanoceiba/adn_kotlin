package com.ceiba.domain.vehicle.car.model

import com.ceiba.domain.vehicle.model.Vehicle
import java.time.LocalDateTime

class Car(licensePlate: String, entryDate: LocalDateTime) : Vehicle(licensePlate, entryDate) {
    override fun saveVehicle() {
        TODO("Not yet implemented")
    }

    override fun parkingCost(): Int {
        TODO("Not yet implemented")
    }

}
