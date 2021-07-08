package com.ceiba.infrastructure.motorcycle.translate

import com.ceiba.domain.vehicle.motorcycle.model.Motorcycle
import com.ceiba.infrastructure.motorcycle.database.entity.MotorcycleEntity
import java.time.LocalDateTime
import java.util.function.Consumer

class MotorcycleTranslate {

    companion object {

        fun translateMotorcycleFromDomainToDB(motorcycle: Motorcycle): MotorcycleEntity {
            val motorcycleEntity = MotorcycleEntity()
            motorcycleEntity.licensePlate = motorcycle.licensePlate
            motorcycleEntity.entryDate = motorcycle.entryDate.toString()
            motorcycleEntity.cylinderCapacity = Integer.parseInt(motorcycle.cylinderCapacity)
            return motorcycleEntity
        }

        fun translateMotorcycleFromDBToDomain(motorcycleEntity: MotorcycleEntity): Motorcycle {
            val localDateTime = LocalDateTime.parse(motorcycleEntity.entryDate)
            return Motorcycle(
                motorcycleEntity.licensePlate,
                localDateTime,
                motorcycleEntity.cylinderCapacity.toString()
            )
        }

        fun translateMotorcycleListFromDBToDomain(motorcycleList: List<MotorcycleEntity>): List<Motorcycle> {
            val translatedMotorcycleList: MutableList<Motorcycle> = ArrayList()
            motorcycleList.forEach(Consumer { motorcycle: MotorcycleEntity? ->
                translatedMotorcycleList.add(translateMotorcycleFromDBToDomain(motorcycle!!))
            })
            return translatedMotorcycleList
        }
    }
}
