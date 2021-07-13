package com.ceiba.adn_kotlin.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ceiba.adn_kotlin.R
import com.ceiba.domain.parking.exception.GlobalException
import com.ceiba.domain.parking.service.ParkingService
import com.ceiba.domain.vehicle.model.Vehicle
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class ParkingViewModel @Inject constructor(
    private val parkingService: ParkingService,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val vehicleList: MutableLiveData<List<Vehicle>> = MutableLiveData()
    private val saveVehicleResponse: MutableLiveData<String> = MutableLiveData()
    private val parkingCost: MutableLiveData<Int> = MutableLiveData()
    private val executorService: ExecutorService = Executors.newFixedThreadPool(4)

    fun saveVehicle(vehicle: Vehicle): LiveData<String> {
        try {
            executorService.execute { vehicle.saveVehicle(parkingService) }
            getVehicleMutableList()
            saveVehicleResponse.value = context.getString(R.string.vehicleSaved)
        } catch (e: Exception) {
            throw GlobalException(context.getString(R.string.vehicleNotSavedException), e)
        }
        return saveVehicleResponse
    }

    fun getVehicleMutableList(): MutableLiveData<List<Vehicle>> {
        executorService.execute { vehicleList.postValue(parkingService.getVehicles()) }
        return vehicleList
    }

    fun collectParkingService(vehicle: Vehicle): LiveData<Int> {
        executorService.execute { parkingCost.postValue(vehicle.parkingCost(parkingService)) }
        getVehicleMutableList()
        return parkingCost
    }
}
