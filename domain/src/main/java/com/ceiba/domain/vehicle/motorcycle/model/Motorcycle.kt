package com.ceiba.domain.vehicle.motorcycle.model

import com.ceiba.domain.parking.exception.GlobalException
import com.ceiba.domain.parking.service.ParkingService
import com.ceiba.domain.vehicle.model.Vehicle
import java.lang.Exception
import java.time.LocalDateTime

class Motorcycle(
    licensePlate: String,
    entryDate: LocalDateTime,
    val cylinderCapacity: String
) : Vehicle(licensePlate, entryDate) {

    init {
        validateCylinderCapacity()
    }

    private fun validateCylinderCapacity() {
        if ("" == cylinderCapacity) {
            throw GlobalException("Ingrese el cilindraje del vehiculo", Exception())
        } else {
            cylinderCapacity.toInt()
        }
    }

    override fun saveVehicle(parkingService: ParkingService) {
        parkingService.saveMotorcycle(this, entryDate.dayOfWeek.value)
    }

    override fun parkingCost(parkingService: ParkingService): Int {
        val cost = parkingService.motorcycleParkingCost(this, LocalDateTime.now())
        parkingService.deleteMotorcycle(this)
        return cost
    }
}