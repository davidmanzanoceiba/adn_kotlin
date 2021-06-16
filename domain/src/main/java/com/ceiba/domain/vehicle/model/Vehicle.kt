package com.ceiba.domain.vehicle.model

import java.time.LocalDateTime

abstract class Vehicle(
    protected val licensePlate: String,
    protected val entryDate: LocalDateTime
) {
    init {
        validateLicensePlate()
    }

    private fun validateLicensePlate() {
        if ("" == licensePlate){
            //exception
        }
    }

    abstract fun saveVehicle()

    abstract fun parkingCost() : Int
}
