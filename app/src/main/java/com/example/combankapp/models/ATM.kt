package com.example.combankapp.models

data class ATM(
    val id: String,
    val name: String,
    val location: Location,
    val address: String
)

data class Location(
    val lat: Double,
    val lon: Double
)