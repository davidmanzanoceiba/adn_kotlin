package com.ceiba.domain.vehicle.model

import java.time.LocalDateTime

abstract class Vehicle(val licensePlate: String, val entryDate: LocalDateTime) {
    init {
        validateLicensePlate()
    }

    private fun validateLicensePlate() {
        if ("" == licensePlate) {
            //exception
        }
    }

    abstract fun saveVehicle()

    abstract fun parkingCost(): Int
}
