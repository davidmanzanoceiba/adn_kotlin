package com.ceiba.domain

import com.ceiba.domain.vehicle.car.model.Car
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class VehicleUnitTest {

    private val nullLicensePlate = "Ingrese la placa del vehiculo"

    @Test
    fun setLicensePlate_nullLicensePlate_isCorrect() {
        //Arrange
        val licensePlate = ""
        val entryDate = LocalDateTime
            .of(2021, 5, 23, 13, 57, 0)
        //Act
        try {
            Car(licensePlate, entryDate)
        } catch (e: Exception) {
            //Assert
            assertEquals(nullLicensePlate, e.message)
        }
    }

}