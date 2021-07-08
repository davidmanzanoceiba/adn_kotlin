package com.ceiba.infrastructure.motorcycle.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ceiba.infrastructure.motorcycle.database.entity.MotorcycleEntity

@Dao
interface MotorcycleDao {

    @Insert
    fun saveMotorcycle(motorcycleEntity: MotorcycleEntity)

    @Query("SELECT * FROM motorcycle")
    fun getMotorcycles(): List<MotorcycleEntity>

    @Query("DELETE FROM motorcycle WHERE licensePlate = :licensePlate")
    fun deleteMotorcycles(licensePlate: String)

    @Query("SELECT COUNT(*) FROM motorcycle")
    fun getNumberOfMotorcycles(): Int
}
