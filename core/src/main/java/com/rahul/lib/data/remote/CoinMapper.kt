package com.rahul.lib.data.remote

import com.rahul.lib.data.models.CoinEntity
import javax.inject.Inject
//
//class CoinMapper @Inject constructor() : Mapper<CoinNetwork, CoinEntity> {
//    override fun from(e: CoinEntity?): CoinNetwork {
//        return CoinNetwork(
//            current_price = e?.current_price,
//            image = e?.image,
//            name = e?.name,
//            price_change_percentage_24h = e?.price_change_percentage_24h,
//            symbol = e?.symbol
//        )
//    }
//    override fun to(t: CoinNetwork?): CoinEntity {
//        return CoinEntity(
//            current_price = t?.current_price,
//            image = t?.image,
//            name = t?.name,
//            price_change_percentage_24h = t?.price_change_percentage_24h,
//            symbol = t?.symbol
//        )
//    }
//}