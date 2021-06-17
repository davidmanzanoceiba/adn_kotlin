package com.ceiba.domain.parking.model

class Parking {
    companion object {
        val MOTORCYCLE_SERVICE_COST: ServiceCost = ServiceCost(500, 4000, 2000)
        val CAR_SERVICE_COST: ServiceCost = ServiceCost(1000, 8000, 0)
        const val HOUR_LIMIT = 9
        const val CYLINDER_CAPACITY_LIMIT = 500
        const val FIRST_LETTER_LICENSE_PLATE = "A"
        const val SUNDAY = 7
        const val MONDAY = 1
        const val MAX_NUMBER_OF_CARS = 20
        const val MAX_NUMBER_OF_MOTORCYCLES = 10
        const val PARKING_LIMIT = "Se ha alcanzado el limite de capacidad del parqueadero"
        const val RESTRICTED_LICENSE_PLATE =
            "La placa del vehiculo esta restringida por el dia de hoy"
    }
}
