package com.ceiba.domain.vehicle.model

import com.ceiba.domain.parking.service.ParkingService
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

    abstract fun saveVehicle(parkingService: ParkingService)

    abstract fun parkingCost(parkingService: ParkingService): Int
}
