package com.ceiba.domain.vehicle.car.model

import com.ceiba.domain.parking.service.ParkingService
import com.ceiba.domain.vehicle.model.Vehicle
import java.time.LocalDateTime

class Car(licensePlate: String, entryDate: LocalDateTime) : Vehicle(licensePlate, entryDate) {

    override fun saveVehicle(parkingService: ParkingService) {
        parkingService.saveCar(this, entryDate.dayOfWeek.value)
    }

    override fun parkingCost(parkingService: ParkingService): Int {
        val cost: Int = parkingService.carParkingCost(this, LocalDateTime.now())
        parkingService.deleteCar(this)
        return cost
    }
}
