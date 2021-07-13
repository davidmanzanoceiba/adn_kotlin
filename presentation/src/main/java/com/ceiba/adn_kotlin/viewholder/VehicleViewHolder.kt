package com.ceiba.adn_kotlin.viewholder

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ceiba.adn_kotlin.ParkingServiceActivity
import com.ceiba.adn_kotlin.R
import com.ceiba.domain.vehicle.model.Vehicle

class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textViewLicensePlate: TextView = itemView.findViewById(R.id.textViewLicensePlate)
    private val textViewEntryDate: TextView = itemView.findViewById(R.id.textViewEntryDate)
    private val buttonCollect: TextView = itemView.findViewById(R.id.buttonCollect)


    fun bindData(vehicle: Vehicle, activity: Activity) {
        textViewLicensePlate.text = vehicle.licensePlate
        textViewEntryDate.text = vehicle.entryDate.toString()
        buttonCollect.setOnClickListener {
            val parkingServiceActivity: ParkingServiceActivity = activity as ParkingServiceActivity
            parkingServiceActivity.collectParkingService(vehicle)
        }
    }

}