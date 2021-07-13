package com.ceiba.infrastructure.car.repository

import android.content.Context
import com.ceiba.domain.parking.exception.GlobalException
import com.ceiba.domain.vehicle.car.model.Car
import com.ceiba.domain.vehicle.car.repository.CarRepository
import com.ceiba.infrastructure.car.database.entity.CarEntity
import com.ceiba.infrastructure.car.translate.CarTranslate
import com.ceiba.infrastructure.database.ParkingDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception
import javax.inject.Inject

class CarRepositoryRoom @Inject constructor(@ApplicationContext val context: Context) :
    CarRepository {

    private val parkingDatabase = ParkingDatabase.getInstance(context)

    override fun saveCar(car: Car) {
        val carEntity = CarTranslate.translateCarFromDomainToDB(car)
        parkingDatabase.carDao().saveCar(carEntity)
    }

    override fun deleteCar(car: Car) {
        val carEntity = CarTranslate.translateCarFromDomainToDB(car)
        parkingDatabase.carDao().deleteCar(carEntity.licensePlate)
    }

    override fun getNumberOfCars(): Int {
        val numberOfCars: Int
        try {
            numberOfCars = parkingDatabase.carDao().getNumberOfCars()
        } catch (e: Exception) {
            throw GlobalException("Error al obtener el numero de carros", e)
        }
        return numberOfCars
    }

    override fun getCars(): List<Car> {
        val carList: MutableList<Car> = ArrayList()
        try {
            val carEntityList: List<CarEntity> = parkingDatabase.carDao().getCars()
            carList.addAll(CarTranslate.translateCarListFromDBToDomain(carEntityList))
        } catch (e: Exception) {
            throw GlobalException("Error al obtener la lista de carros", e)
        }
        return carList
    }
}
