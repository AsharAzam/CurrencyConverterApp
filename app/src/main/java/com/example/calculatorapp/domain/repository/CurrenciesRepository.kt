package com.example.calculatorapp.domain.repository

import com.example.calculatorapp.data.local.CurrencyInfoEntity
import com.example.calculatorapp.domain.model.CurrenciesInfo
import com.example.calculatorapp.util.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface CurrenciesRepository {

    suspend fun getCurrenciesListings(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<CurrenciesInfo>>>


}