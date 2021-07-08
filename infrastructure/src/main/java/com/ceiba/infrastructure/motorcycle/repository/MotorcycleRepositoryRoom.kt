package com.ceiba.infrastructure.motorcycle.repository

import android.content.Context
import com.ceiba.domain.parking.exception.GlobalException
import com.ceiba.domain.vehicle.motorcycle.model.Motorcycle
import com.ceiba.domain.vehicle.motorcycle.repository.MotorcycleRepository
import com.ceiba.infrastructure.database.ParkingDatabase
import com.ceiba.infrastructure.motorcycle.database.entity.MotorcycleEntity
import com.ceiba.infrastructure.motorcycle.translate.MotorcycleTranslate
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MotorcycleRepositoryRoom: MotorcycleRepository {

    private var parkingDatabase: ParkingDatabase = TODO()

    @Inject
    fun MotorcycleRepositoryRoom(@ApplicationContext context: Context) {
        parkingDatabase = ParkingDatabase.getInstance(context)
    }

    override fun saveMotorcycle(motorcycle: Motorcycle) {
        val motorcycleEntity = MotorcycleTranslate.translateMotorcycleFromDomainToDB(motorcycle)
        parkingDatabase.motorcycleDao().saveMotorcycle(motorcycleEntity)
    }

    override fun deleteMotorcycle(motorcycle: Motorcycle) {
        val motorcycleEntity = MotorcycleTranslate.translateMotorcycleFromDomainToDB(motorcycle)
        parkingDatabase.motorcycleDao().deleteMotorcycles(motorcycleEntity.licensePlate)
    }

    override fun getNumberOfMotorcycles(): Int {
        val numberOfMotorcycles: Int
        try {
            numberOfMotorcycles = parkingDatabase.motorcycleDao().getNumberOfMotorcycles()
        } catch (e: Exception) {
            throw GlobalException("Error al obtener la cantidad de motos", e)
        }
        return numberOfMotorcycles
    }

    override fun getMotorcycles(): List<Motorcycle> {
        val motorcycleList: MutableList<Motorcycle> = ArrayList()
        try {
            val motorcycleEntityList: List<MotorcycleEntity> = parkingDatabase.motorcycleDao().getMotorcycles()
            motorcycleList.addAll(MotorcycleTranslate.translateMotorcycleListFromDBToDomain(motorcycleEntityList))
        } catch (e: Exception) {
            throw GlobalException("Error al obtener la lista de motos", e)
        }
        return motorcycleList
    }
}
