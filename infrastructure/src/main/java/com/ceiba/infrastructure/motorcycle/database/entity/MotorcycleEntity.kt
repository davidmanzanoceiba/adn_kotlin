package com.ceiba.infrastructure.motorcycle.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "motorcycle")
class MotorcycleEntity {

    @PrimaryKey(autoGenerate = true)
    var id = 0
    var licensePlate = ""
    var entryDate = ""
    var cylinderCapacity = 0
}
