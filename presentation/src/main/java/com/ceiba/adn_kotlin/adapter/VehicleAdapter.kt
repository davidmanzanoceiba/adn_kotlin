package com.ceiba.adn_kotlin.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ceiba.adn_kotlin.R
import com.ceiba.adn_kotlin.viewholder.VehicleViewHolder
import com.ceiba.domain.vehicle.model.Vehicle

class VehicleAdapter(private val vehicleList: List<Vehicle>, private val activity: Activity) :
    RecyclerView.Adapter<VehicleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VehicleViewHolder(layoutInflater.inflate(R.layout.vehicle_item, parent, false))
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val vehicle = vehicleList[position]
        holder.bindData(vehicle, activity)
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }
}
