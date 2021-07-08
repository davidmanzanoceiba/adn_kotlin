package com.ceiba.infrastructure.car.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car")
class CarEntity {

    @PrimaryKey(autoGenerate = true)
    private var id = 0
    var licensePlate = ""
    var entryDate = ""
}
