package com.ceiba.domain.parking.service

import com.ceiba.domain.parking.exception.GlobalException
import com.ceiba.domain.parking.model.Parking
import com.ceiba.domain.parking.model.ServiceCost
import com.ceiba.domain.vehicle.car.model.Car
import com.ceiba.domain.vehicle.car.repository.CarRepository
import com.ceiba.domain.vehicle.model.Vehicle
import com.ceiba.domain.vehicle.motorcycle.model.Motorcycle
import com.ceiba.domain.vehicle.motorcycle.repository.MotorcycleRepository
import java.lang.Exception
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.math.ceil

class ParkingService @Inject constructor(
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
                throw GlobalException(Parking.PARKING_LIMIT, Exception())
            }
            validateLicensePlate(car.licensePlate, currentDay) -> {
                throw GlobalException(Parking.RESTRICTED_LICENSE_PLATE, Exception())
            }
            else -> {
                carRepository.saveCar(car)
            }
        }
    }

    fun saveMotorcycle(motorcycle: Motorcycle, currentDay: Int) {
        val numberOfCars = motorcycleRepository.getNumberOfMotorcycles()
        when {
            numberOfCars == Parking.MAX_NUMBER_OF_MOTORCYCLES -> {
                throw GlobalException(Parking.PARKING_LIMIT, Exception())
            }
            validateLicensePlate(motorcycle.licensePlate, currentDay) -> {
                throw GlobalException(Parking.RESTRICTED_LICENSE_PLATE, Exception())
            }
            else -> {
                motorcycleRepository.saveMotorcycle(motorcycle)
            }
        }
    }

    fun validateLicensePlate(licensePlate: String, currentDay: Int): Boolean {
        return (licensePlate.startsWith(Parking.FIRST_LETTER_LICENSE_PLATE)
                && (currentDay == Parking.SUNDAY || currentDay == Parking.MONDAY))
    }

    fun deleteCar(car: Car) {
        carRepository.deleteCar(car)
    }

    fun deleteMotorcycle(motorcycle: Motorcycle) {
        motorcycleRepository.deleteMotorcycle(motorcycle)
    }

    fun getVehicles(): MutableList<Vehicle> {
        val vehicleList: MutableList<Vehicle> = ArrayList()
        vehicleList.addAll(carRepository.getCars())
        vehicleList.addAll(motorcycleRepository.getMotorcycles())
        return vehicleList
    }

    fun carParkingCost(car: Car, exitDate: LocalDateTime): Int {
        val carServiceCost: ServiceCost = Parking.CAR_SERVICE_COST
        return calculateParkingCost(car, exitDate, carServiceCost)
    }

    fun motorcycleParkingCost(motorcycle: Motorcycle, exitDate: LocalDateTime): Int {
        val motorcycleServiceCost: ServiceCost = Parking.MOTORCYCLE_SERVICE_COST
        var parkingCost = calculateParkingCost(motorcycle, exitDate, motorcycleServiceCost)
        if (motorcycle.cylinderCapacity.toInt() > Parking.CYLINDER_CAPACITY_LIMIT)
            parkingCost += motorcycleServiceCost.surplus
        return parkingCost
    }

    private fun calculateParkingCost(
        vehicle: Vehicle, exitDate: LocalDateTime, serviceCost: ServiceCost
    ): Int {
        val hourLimit: Int = Parking.HOUR_LIMIT
        val priceHour: Int = serviceCost.priceHour
        val priceDay: Int = serviceCost.priceDay
        val parkingCost: Int
        val parkingTime: Int = getParkingTime(vehicle.entryDate, exitDate)
        if (parkingTime < hourLimit) {
            parkingCost = parkingTime * priceHour
        } else {
            var days = parkingTime / HOURS_IN_A_DAY
            val hours = parkingTime % HOURS_IN_A_DAY
            if (hours >= hourLimit) {
                days += 1
                parkingCost = days * priceDay
            } else {
                parkingCost = (days * priceDay) + (hours * priceHour)
            }
        }
        return parkingCost
    }

    fun getParkingTime(entryDate: LocalDateTime, exitDate: LocalDateTime): Int {
        val timeElapsed: Long = entryDate.until(exitDate, ChronoUnit.MILLIS)
        return ceil((timeElapsed / MILLISECONDS_IN_AN_HOUR).toDouble()).toInt()
    }
}
