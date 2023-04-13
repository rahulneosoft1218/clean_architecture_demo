package com.rahul.data_module.repositories

import com.rahul.data_module.di.DataModule
import com.rahul.data_module.models.requests.GetAllCoinRequest
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

public class CoinRepositoryTest{
   private val request = GetAllCoinRequest("usd","market_cap_desc",1,2,false)


    @Inject
    lateinit var repository : CoinRepository

    @Before
    fun setUp(){
        val dataModule = DataModule()
        repository = CoinRepository(dataModule.provideApiService(dataModule.provideOkhttpClient()))
//        val component = Dag


    }

    @Test
    fun getAllCoinTest(): Unit = runBlocking{

        launch(Dispatchers.IO) {
            val data = repository.getAllCoins(request)

        }



    }


}