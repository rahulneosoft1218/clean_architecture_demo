package com.rahul.data_module.models.requests

data class GetCoinDetailRequest(
    val id: String
) : ApisRequest("coins/$id")

