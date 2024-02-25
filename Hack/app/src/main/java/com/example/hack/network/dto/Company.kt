package com.example.hack.network.dto

import com.google.gson.annotations.SerializedName

data class Company(
    val id: Int,
    val name: String,
    val curatorUserId: String,
    val description: String,
    val money: Int
)

data class CompanyResponse(
    val companies: List<Company>
)

data class CompanyFull(
    @SerializedName("company")
    val companyData: CompanyData
)

data class CompanyData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: Int,
    @SerializedName("curator_user_id")
    val curatorUserId: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("money")
    val money: Int
)