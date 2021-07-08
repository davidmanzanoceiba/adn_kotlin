package com.ceiba.infrastructure.car.translate

import com.ceiba.domain.vehicle.car.model.Car
import com.ceiba.infrastructure.car.database.entity.CarEntity
import java.time.LocalDateTime
import java.util.ArrayList
import java.util.function.Consumer

class CarTranslate {
    companion object {
        fun translateCarFromDomainToDB(car: Car): CarEntity {
            val carEntity = CarEntity()
            carEntity.licensePlate = car.licensePlate
            carEntity.entryDate = car.entryDate.toString()
            return carEntity
        }

        fun translateCarFromDBToDomain(carEntity: CarEntity): Car {
            val localDateTime = LocalDateTime.parse(carEntity.entryDate)
            return Car(carEntity.licensePlate, localDateTime)
        }

        fun translateCarListFromDBToDomain(carList: List<CarEntity>): List<Car> {
            val translatedCarList: MutableList<Car> = ArrayList()
            carList.forEach(Consumer { car: CarEntity? ->
                translatedCarList.add(translateCarFromDBToDomain(car!!))
            })
            return translatedCarList
        }
    }
}
