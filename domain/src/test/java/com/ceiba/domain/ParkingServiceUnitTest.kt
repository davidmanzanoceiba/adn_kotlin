package com.ceiba.domain

import com.ceiba.domain.parking.service.ParkingService
import com.ceiba.domain.vehicle.car.model.Car
import com.ceiba.domain.vehicle.car.repository.CarRepository
import com.ceiba.domain.vehicle.motorcycle.repository.MotorcycleRepository
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
import java.time.LocalDateTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ParkingServiceUnitTest {

    @Mock
    private val carRepository: CarRepository = Mockito.mock(CarRepository::class.java)

    @Mock
    private val motorcycleRepository: MotorcycleRepository =
        Mockito.mock(MotorcycleRepository::class.java)

    private val parkingService: ParkingService = ParkingService(carRepository, motorcycleRepository)

    @Test
    fun getParkingTime_4HoursDifference_isCorrect() {
        //Arrange
        val entryDate: LocalDateTime = LocalDateTime
            .of(2021, 5, 23, 13, 57, 0)
        val exitDate: LocalDateTime = LocalDateTime
            .of(2021, 5, 23, 17, 57, 0)
        val car = Car("YMU-95C", entryDate)
        //Act
        val parkingTime: Int = parkingService.getParkingTime(car.entryDate, exitDate)
        //Assert
        assertEquals(4, parkingTime)
    }

    @Test
    fun carParkingCost_5HoursParking_isCorrect() {
        //Arrange
        val entryDate: LocalDateTime = LocalDateTime
                .of(2021, 5, 23, 13, 57, 0)
        val exitDate: LocalDateTime = LocalDateTime
                .of(2021, 5, 23, 18, 57, 0)
        val car = Car("YMU-95C", entryDate)
        //Act
        val parkingCost: Int = parkingService.carParkingCost(car, exitDate)
        //Assert
        assertEquals(5000, parkingCost)
    }
}