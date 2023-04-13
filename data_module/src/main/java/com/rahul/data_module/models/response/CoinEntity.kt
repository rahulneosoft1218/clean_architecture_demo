package com.rahul.data_module.models.response

data class CoinEntity(
    val current_price: Float?,
    val image: String?,
    val name: String?,
    val price_change_percentage_24h: Double?,
    val symbol: String?
)