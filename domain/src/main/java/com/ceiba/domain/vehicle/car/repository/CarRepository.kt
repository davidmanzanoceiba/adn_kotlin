package com.ceiba.domain.vehicle.car.repository

import com.ceiba.domain.vehicle.car.model.Car

interface CarRepository {

    fun saveCar(car: Car)

    fun deleteCar(car: Car)

    fun getNumberOfCars(): Int

    fun getCars(): List<Car>

}
