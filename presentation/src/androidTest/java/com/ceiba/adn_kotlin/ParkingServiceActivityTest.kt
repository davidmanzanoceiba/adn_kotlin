package com.ceiba.adn_kotlin

import androidx.test.core.app.ActivityScenario
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner

import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class ParkingServiceActivityTest {

    @Test
    fun saveCar_carSaved_carOnDisplay() {
        ActivityScenario.launch(ParkingServiceActivity::class.java).use {
            //Arrange
            val licensePlate = "YMU-950"
            //Act
            ParkingServiceActivityResources()
                .clickButton(ParkingServiceActivityResources.radioButtonCar)
            ParkingServiceActivityResources()
                .editText(ParkingServiceActivityResources.editTextLicensePlate, licensePlate)
            //Assert
            ParkingServiceActivityResources()
                .clickButton(ParkingServiceActivityResources.buttonSaveVehicle)
        }
    }

    @Test
    fun saveMotorcycle_motorcycleSaved_isCorrect() {
        ActivityScenario.launch(ParkingServiceActivity::class.java).use {
            //Arrange
            val licensePlate = "YMU-95C"
            val cylinderCapacity = "650"
            //Act
            ParkingServiceActivityResources()
                .clickButton(ParkingServiceActivityResources.radioButtonMotorcycle)
            ParkingServiceActivityResources()
                .editText(ParkingServiceActivityResources.editTextLicensePlate, licensePlate)
            ParkingServiceActivityResources()
                .editText(
                    ParkingServiceActivityResources.editTextCylinderCapacity,
                    cylinderCapacity
                )
            //Assert
            ParkingServiceActivityResources()
                .clickButton(ParkingServiceActivityResources.buttonSaveVehicle)
        }
    }
}