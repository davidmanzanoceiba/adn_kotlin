package com.ceiba.infrastructure.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ceiba.infrastructure.car.database.dao.CarDao
import com.ceiba.infrastructure.car.database.entity.CarEntity
import com.ceiba.infrastructure.motorcycle.database.dao.MotorcycleDao
import com.ceiba.infrastructure.motorcycle.database.entity.MotorcycleEntity

@Database(entities = [CarEntity::class, MotorcycleEntity::class], version = 1, exportSchema = false)
abstract class ParkingDatabase : RoomDatabase() {

    abstract fun carDao(): CarDao

    abstract fun motorcycleDao(): MotorcycleDao

    companion object {
        private var databaseInstance: ParkingDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ParkingDatabase {
            if (databaseInstance == null) {
                databaseInstance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        ParkingDatabase::class.java,
                        "parking_database"
                    )
                    .build()
            }
            return databaseInstance!!
        }
    }
}
