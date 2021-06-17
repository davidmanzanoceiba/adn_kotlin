package com.ceiba.domain.parking.service

import com.ceiba.domain.parking.model.Parking
import com.ceiba.domain.vehicle.car.model.Car
import com.ceiba.domain.vehicle.car.repository.CarRepository
import com.ceiba.domain.vehicle.motorcycle.repository.MotorcycleRepository

class ParkingService(
    private val carRepository: CarRepository,
    private val motorcycleRepository: MotorcycleRepository
) {
    companion object {
        private const val MILLISECONDS_IN_AN_HOUR = 3600000
        private const val HOURS_IN_A_DAY = 24
    }

    fun saveCar(car: Car, currentDay: Int) {
        val numberOfCars = carRepository.getNumberOfCars()
        when {
            numberOfCars == Parking.MAX_NUMBER_OF_CARS -> {
                //ParkingLimitException
            }
            validateLicensePlate(car.licensePlate, currentDay) -> {
                //RestrictedAccessByDayException
            }
            else -> {
                carRepository.saveCar(car)
            }
        }
    }

    fun validateLicensePlate(licensePlate: String, currentDay: Int): Boolean {
        return (licensePlate.startsWith(Parking.FIRST_LETTER_LICENSE_PLATE)
                && (currentDay == Parking.SUNDAY || currentDay == Parking.MONDAY))
    }

}
