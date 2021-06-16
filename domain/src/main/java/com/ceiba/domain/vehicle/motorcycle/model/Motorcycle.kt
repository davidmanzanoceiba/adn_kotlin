package com.ceiba.domain.vehicle.motorcycle.model

import com.ceiba.domain.vehicle.model.Vehicle
import java.time.LocalDateTime

class Motorcycle(
    licensePlate: String,
    entryDate: LocalDateTime,
    private val cylinderCapacity: String
) : Vehicle(licensePlate, entryDate) {

    init {
        validateCylinderCapacity()
    }

    private fun validateCylinderCapacity() {
        if ("" == cylinderCapacity) {
            //exception
        } else {
            cylinderCapacity.toInt()
        }
    }

    override fun saveVehicle() {
        TODO("Not yet implemented")
    }

    override fun parkingCost(): Int {
        TODO("Not yet implemented")
    }
}