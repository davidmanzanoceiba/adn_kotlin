package com.ceiba.adn_kotlin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ceiba.adn_kotlin.adapter.VehicleAdapter
import com.ceiba.adn_kotlin.databinding.ActivityParkingServiceBinding
import com.ceiba.adn_kotlin.viewmodel.ParkingViewModel
import com.ceiba.domain.parking.exception.GlobalException
import com.ceiba.domain.vehicle.car.model.Car
import com.ceiba.domain.vehicle.model.Vehicle
import com.ceiba.domain.vehicle.motorcycle.model.Motorcycle
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class ParkingServiceActivity : AppCompatActivity() {

    private lateinit var vehicleAdapter: VehicleAdapter
    private val parkingViewModel: ParkingViewModel by viewModels()
    private lateinit var binding: ActivityParkingServiceBinding
    private var motorcycleType: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParkingServiceBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        initElements()
        onClickManager()
        validateSavedInstance(savedInstanceState)
    }

    private fun initElements() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewVehicles.layoutManager = linearLayoutManager
        parkingViewModel.getVehicleMutableList().observe(this, this::updateAdapter)
    }

    private fun updateAdapter(vehicleList: List<Vehicle>) {
        vehicleAdapter = VehicleAdapter(vehicleList, this)
        binding.recyclerViewVehicles.adapter = vehicleAdapter
    }

    private fun onClickManager() {
        binding.radioButtonMotorcycle.setOnClickListener {
            motorcycleType = true
            activateCylinderCapacityEditText()
        }
        binding.radioButtonCar.setOnClickListener {
            motorcycleType = false
            activateCylinderCapacityEditText()
        }
        binding.buttonSaveVehicle.setOnClickListener {
            saveVehicle()
        }
    }

    private fun activateCylinderCapacityEditText() {
        if (motorcycleType) {
            binding.radioButtonMotorcycle.isChecked = true
            binding.editTextCylinderCapacity.visibility = View.VISIBLE
        } else {
            binding.editTextCylinderCapacity.visibility = View.GONE
            binding.radioButtonCar.isChecked = true
        }
    }

    private fun saveVehicle() {
        val cylinderCapacity = binding.editTextCylinderCapacity.text.toString()
        val licensePlate = binding.editTextLicensePlate.text.toString()
        try {
            when {
                binding.radioButtonCar.isChecked -> {
                    val vehicle: Vehicle = Car(licensePlate, LocalDateTime.now())
                    parkingViewModel.saveVehicle(vehicle).observe(this, { result ->
                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                    })
                    clearFields()
                }
                binding.radioButtonMotorcycle.isChecked -> {
                    val vehicle: Vehicle =
                        Motorcycle(licensePlate, LocalDateTime.now(), cylinderCapacity)
                    parkingViewModel.saveVehicle(vehicle).observe(this, { result ->
                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                    })
                    clearFields()
                }
                else -> {
                    Toast.makeText(this, "Seleccione el tipo de vehiculo", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } catch (e: GlobalException) {
            Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
        }
        vehicleAdapter.notifyDataSetChanged()
    }

    private fun clearFields() {
        binding.editTextCylinderCapacity.setText("")
        binding.editTextLicensePlate.setText("")
    }

    fun collectParkingService(vehicle: Vehicle?) {
        parkingViewModel.collectParkingService(vehicle!!).observe(this, { parkingServiceCost ->
            Toast.makeText(this, "Total a pagar: $parkingServiceCost", Toast.LENGTH_SHORT).show()
            vehicleAdapter.notifyDataSetChanged()
        })
    }

    private fun validateSavedInstance(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            motorcycleType = savedInstanceState.getBoolean("motorcycleType")
            activateCylinderCapacityEditText()
        }
    }

    @Override
    override fun onSaveInstanceState(@NonNull outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("motorcycleType", motorcycleType)
    }

}
