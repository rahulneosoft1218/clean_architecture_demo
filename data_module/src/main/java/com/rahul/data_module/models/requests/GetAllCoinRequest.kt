package com.rahul.data_module.models.requests

data class GetAllCoinRequest(
    val currency: String,
    val order: String,
    val perPage: Int,
    val page: Int,
    val sparkline: Boolean
) : ApisRequest("coins/markets")

