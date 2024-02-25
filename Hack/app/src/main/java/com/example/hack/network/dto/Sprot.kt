package com.example.hack.network.dto

data class Sport (
    val id: Int,
    val name: String,
)

data class SportResponse
    (
    val sport_activities: List<Sport>
)