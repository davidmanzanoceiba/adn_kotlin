package com.ceiba.domain

import com.ceiba.domain.parking.exception.GlobalException
import com.ceiba.domain.vehicle.motorcycle.model.Motorcycle
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class MotorcycleUnitTest {

    private val nullCylinderCapacity = "Ingrese el cilindraje del vehiculo"

    @Test
    fun setCylinderCapacity_nullCylinderCapacity_isCorrect() {
        //Arrange
        val licensePlate = "HUC-49"
        val entryDate: LocalDateTime = LocalDateTime
                .of(2021, 5, 23, 13, 57, 0)
        //Act
        try{
            Motorcycle(licensePlate, entryDate, "")
        } catch (e: GlobalException) {
            //Assert
            assertEquals(nullCylinderCapacity, e.message)
        }
    }
}
