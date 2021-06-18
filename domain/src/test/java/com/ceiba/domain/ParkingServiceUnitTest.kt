package com.ceiba.domain

import com.ceiba.domain.parking.exception.GlobalException
import com.ceiba.domain.parking.model.Parking
import com.ceiba.domain.parking.service.ParkingService
import com.ceiba.domain.vehicle.car.model.Car
import com.ceiba.domain.vehicle.car.repository.CarRepository
import com.ceiba.domain.vehicle.motorcycle.model.Motorcycle
import com.ceiba.domain.vehicle.motorcycle.repository.MotorcycleRepository
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import java.time.LocalDateTime

class ParkingServiceUnitTest {

    @Mock
    private val carRepository: CarRepository = Mockito.mock(CarRepository::class.java)

    @Mock
    private val motorcycleRepository: MotorcycleRepository =
        Mockito.mock(MotorcycleRepository::class.java)

    private val parkingService: ParkingService = ParkingService(carRepository, motorcycleRepository)
    private val licensePlateStartsWithA = "AMU-95C"
    private val licensePlateStartsWithoutA = "YMU-95C"

    @Test
    fun getParkingTime_4HoursDifference_isCorrect() {
        //Arrange
        val entryDate: LocalDateTime = LocalDateTime
            .of(2021, 5, 23, 13, 57, 0)
        val exitDate: LocalDateTime = LocalDateTime
            .of(2021, 5, 23, 17, 57, 0)
        val car = Car(licensePlateStartsWithoutA, entryDate)
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
        val car = Car(licensePlateStartsWithoutA, entryDate)
        //Act
        val parkingCost: Int = parkingService.carParkingCost(car, exitDate)
        //Assert
        assertEquals(5000, parkingCost)
    }

    @Test
    fun carParkingCost_24HoursParking_isCorrect() {
        //Arrange
        val entryDate: LocalDateTime = LocalDateTime
                .of(2021, 5, 23, 13, 57, 0)
        val exitDate: LocalDateTime = LocalDateTime
                .of(2021, 5, 24, 13, 57, 0)
        val car = Car(licensePlateStartsWithoutA, entryDate)
        //Act
        val parkingCost: Int = parkingService.carParkingCost(car, exitDate)
        //Assert
        assertEquals(8000, parkingCost)
    }

    @Test
    fun carParkingCost_30HoursParking_isCorrect() {
        //Arrange
        val entryDate: LocalDateTime = LocalDateTime
                .of(2021, 5, 23, 13, 57, 0)
        val exitDate: LocalDateTime = LocalDateTime
                .of(2021, 5, 24, 19, 57, 0)
        val car = Car(licensePlateStartsWithoutA, entryDate)
        //Act
        val parkingCost: Int = parkingService.carParkingCost(car, exitDate)
        //Assert
        assertEquals(14000, parkingCost)
    }

    @Test
    fun carParkingCost_33HoursParking_isCorrect() {
        //Arrange
        val entryDate: LocalDateTime = LocalDateTime
                .of(2021, 5, 23, 13, 57, 0)
        val exitDate: LocalDateTime = LocalDateTime
                .of(2021, 5, 24, 22, 57, 0)
        val car = Car(licensePlateStartsWithoutA, entryDate)
        //Act
        val parkingCost: Int = parkingService.carParkingCost(car, exitDate)
        //Assert
        assertEquals(16000, parkingCost)
    }

    @Test
    fun motorcycleParkingCost_5HoursParkingAnd500CylinderCapacity_isCorrect() {
        //Arrange
        val entryDate: LocalDateTime = LocalDateTime
                .of(2021, 5, 23, 13, 57, 0)
        val exitDate: LocalDateTime = LocalDateTime
                .of(2021, 5, 23, 18, 57, 0)
        val motorcycle = Motorcycle(licensePlateStartsWithoutA, entryDate, "500")
        //Act
        val parkingCost: Int = parkingService.motorcycleParkingCost(motorcycle, exitDate)
        //Assert
        assertEquals(2500, parkingCost)
    }

    @Test
    fun motorcycleParkingCost_24HoursParkingAnd650CylinderCapacity_isCorrect() {
        //Arrange
        val entryDate: LocalDateTime = LocalDateTime
                .of(2021, 5, 23, 13, 57, 0)
        val exitDate: LocalDateTime = LocalDateTime
                .of(2021, 5, 24, 13, 57, 0)
        val motorcycle = Motorcycle(licensePlateStartsWithoutA, entryDate, "650")
        //Act
        val parkingCost: Int = parkingService.motorcycleParkingCost(motorcycle, exitDate)
        //Assert
        assertEquals(6000, parkingCost)
    }

    @Test
    fun validateLicensePlate_startsWithAOnATuesday_isCorrect() {
        //Arrange
        val tuesday = 2
        //Act
        val accessDenied: Boolean = parkingService.validateLicensePlate(licensePlateStartsWithA, tuesday)
        //Assert
        assertFalse(accessDenied)
    }

    @Test
    fun validateLicensePlate_startsWithAOnASunday_isCorrect() {
        //Arrange
        //Act
        val accessDenied: Boolean = parkingService.validateLicensePlate(licensePlateStartsWithA, Parking.SUNDAY)
        //Assert
        assertTrue(accessDenied)
    }

    @Test
    fun saveCar_limitOfCarsInTheParking_isCorrect() {
        //Arrange
        val entryDate = LocalDateTime
            .of(2021, 5, 23, 13, 57, 0)
        val car = Car(licensePlateStartsWithoutA, entryDate)
        Mockito.`when`(carRepository.getNumberOfCars()).thenReturn(Parking.MAX_NUMBER_OF_CARS)
        //Act
        try {
            parkingService.saveCar(car, Parking.SUNDAY)
        } catch (e: GlobalException) {
            //Assert
            assertEquals(Parking.PARKING_LIMIT, e.message)
        }
    }

    @Test
    fun saveCar_restrictedAccessByDay_isCorrect() {
        //Arrange
        val entryDate = LocalDateTime
            .of(2021, 5, 23, 13, 57, 0)
        val car = Car(licensePlateStartsWithA, entryDate)
        Mockito.`when`(carRepository.getNumberOfCars()).thenReturn(7)
        //Act
        try {
            parkingService.saveCar(car, Parking.SUNDAY)
        } catch (e: GlobalException) {
            //Assert
            assertEquals(Parking.RESTRICTED_LICENSE_PLATE, e.message)
        }
    }

    @Test
    fun saveMotorcycle_limitOfMotorcyclesInTheParking_isCorrect() {
        //Arrange
        val entryDate = LocalDateTime
            .of(2021, 5, 23, 13, 57, 0)
        val motorcycle = Motorcycle(licensePlateStartsWithoutA, entryDate, "500")
        Mockito.`when`(motorcycleRepository.getNumberOfMotorcycles())
            .thenReturn(Parking.MAX_NUMBER_OF_CARS)
        //Act
        try {
            parkingService.saveMotorcycle(motorcycle, Parking.SUNDAY)
        } catch (e: GlobalException) {
            //Assert
            assertEquals(Parking.PARKING_LIMIT, e.message)
        }
    }

    @Test
    fun saveMotorcycle_restrictedAccessByDay_isCorrect() {
        //Arrange
        val entryDate = LocalDateTime
            .of(2021, 5, 23, 13, 57, 0)
        val motorcycle = Motorcycle(licensePlateStartsWithA, entryDate, "500")
        Mockito.`when`(motorcycleRepository.getNumberOfMotorcycles()).thenReturn(7)
        //Act
        try {
            parkingService.saveMotorcycle(motorcycle, Parking.SUNDAY)
        } catch (e: GlobalException) {
            //Assert
            assertEquals(Parking.RESTRICTED_LICENSE_PLATE, e.message)
        }
    }
}
