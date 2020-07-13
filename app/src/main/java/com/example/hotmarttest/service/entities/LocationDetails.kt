package com.example.hotmarttest.service.entities

data class LocationDetails(
    val id: Int,
    val name: String,
    val review: Double,
    val type: String,
    val about: String,
    val phone: String,
    val address: String,
    val schedule: Schedules
)