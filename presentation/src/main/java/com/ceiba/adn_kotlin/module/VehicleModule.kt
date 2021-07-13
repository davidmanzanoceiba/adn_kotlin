package com.ceiba.adn_kotlin.module

import com.ceiba.domain.vehicle.car.repository.CarRepository
import com.ceiba.domain.vehicle.motorcycle.repository.MotorcycleRepository
import com.ceiba.infrastructure.car.repository.CarRepositoryRoom
import com.ceiba.infrastructure.motorcycle.repository.MotorcycleRepositoryRoom
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
interface VehicleModule {

    @Binds
    fun injectCarRepository(carRepositoryRoom: CarRepositoryRoom): CarRepository

    @Binds
    fun injectMotorcycleRepository(motorcycleRepositoryRoom: MotorcycleRepositoryRoom): MotorcycleRepository

}