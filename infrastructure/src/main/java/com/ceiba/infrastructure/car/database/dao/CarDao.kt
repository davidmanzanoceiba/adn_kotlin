package com.ceiba.infrastructure.car.database.dao

import androidx.room.Insert
import androidx.room.Query
import com.ceiba.infrastructure.car.database.entity.CarEntity

interface CarDao {

    @Insert
    fun saveCar(carEntity: CarEntity)

    @Query("SELECT * FROM car")
    fun getCars(): List<CarEntity>

    @Query("DELETE FROM car WHERE licensePlate = :licensePlate")
    fun deleteCar(licensePlate: String)

    @Query("SELECT COUNT(*) FROM car")
    fun getNumberOfCars(): Int
}