package com.example.hack.network.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable



data class BaseResponse(
    val status_code: Int,
    val message: String,
    val data: BaseData
)

data class Balance(
    val balance: Int
)

@Serializable
sealed class BaseData


@Serializable
data class UserData(
    val id: Int,
    val status: String,
)

data class UserUpdateBalanceResponse(
    val user_id: Int,
    val balance: String,
)

data class UserInfo(
    val username: String,
    val password: String,
    val office_id: Int,
    val name: String
)

data class WorkerResponse(
    @SerializedName("worker")
    val workerData: WorkerData
)

data class WorkerData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("office_id")
    val officeId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("balance")
    val balance: Int
)