package com.ceiba.domain.vehicle.model

import com.ceiba.domain.parking.exception.GlobalException
import com.ceiba.domain.parking.service.ParkingService
import java.lang.Exception
import java.time.LocalDateTime

abstract class Vehicle(val licensePlate: String, val entryDate: LocalDateTime) {
    init {
        validateLicensePlate()
    }

    private fun validateLicensePlate() {
        if ("" == licensePlate) {
            throw GlobalException("Ingrese la placa del vehiculo", Exception())
        }
    }

    abstract fun saveVehicle(parkingService: ParkingService)

    abstract fun parkingCost(parkingService: ParkingService): Int
}
