package com.ceiba.domain.vehicle.motorcycle.repository

import com.ceiba.domain.vehicle.motorcycle.model.Motorcycle

interface MotorcycleRepository {

    fun saveMotorcycle(motorcycle: Motorcycle)

    fun deleteMotorcycle(motorcycle: Motorcycle)

    fun getNumberOfMotorcycles(): Int

    fun getMotorcycles(): List<Motorcycle>

}
