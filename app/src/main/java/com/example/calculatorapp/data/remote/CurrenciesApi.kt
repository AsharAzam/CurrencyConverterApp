package com.example.calculatorapp.data.remote

import com.example.calculatorapp.domain.model.Rates
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesApi {

    @GET("currencies.json")
    suspend fun getCurrencyList():Map<String,String>

    @GET("latest.json")
    suspend fun getRates(
        @Query("app_id") apiKey: String = APP_ID,
        @Query("base") base: String = BASE_CURRENCY,
        @Query("symbols") symbols: String,
        @Query("prettyprint") prettyprint: Boolean=false,
        @Query("show_alternative") show_alternative: Boolean=false,
    ): Rates


    companion object {
        const val BASE_URL = "https://openexchangerates.org/api/"
        const val APP_ID = "faf651a0d6e54792b24d34f6f8e4d6e2"
        const val BASE_CURRENCY = "USD"

    }
}