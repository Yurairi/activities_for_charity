package com.example.hack.network.dto

data class Activites (
    val id: Int,
    val name: String,
    val type: String,
    val start_date: String,
    val end_date: String,
    val place: String,
    val media: String,
    val src: String,
    val description: String,
    val money: Int
)

data class ActivitesResponse
    (
    val activities: List<Activites>
)