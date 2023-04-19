package com.rahul.data_module.models.response

data class CoinEntity(
    val id: String?,
    val current_price: Double?,
    val image: String?,
    val name: String?,
    val price_change_percentage_24h: Double?,
    val symbol: String?
)