package com.example.cloudswithtile.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HeartRateViewModel(application: Application) : AndroidViewModel(application) {

    private val heartRateManager: HeartRateManager = HeartRateManager(application)

    private val _heartRate = MutableLiveData<Float>()
    val heartRate: LiveData<Float> get() = _heartRate

    fun startListening() {
        heartRateManager.startListening()
    }

    fun stopListening() {
        heartRateManager.stopListening()
    }

    fun updateHeartRate() {
        _heartRate.value = heartRateManager.getHeartRate()
    }
}