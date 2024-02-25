package com.example.hack.network.dto

import com.google.gson.annotations.SerializedName


data class Foundes(
    val id: Int,
    val name: String,
    val curator_user_id: String,
    val description: String,
    val money: Int
)

data class TopDonatorsResponse(
    @SerializedName("top_donators")
    val topDonators: List<TopDonator>
)

data class TopDonator(
    @SerializedName("worker_id")
    val workerId: Int,
    @SerializedName("worker_name")
    val workerName: String,
    @SerializedName("total_donation")
    val totalDonation: Int
)

data class FoundesResponse(
    val fonds: List<Foundes>
)

data class FondResponse(
    @SerializedName("fond")
    val fondData: FondData
)

data class FondData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: Int,
    @SerializedName("curator_user_id")
    val curatorUserId: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("balance")
    val balance: Int
)