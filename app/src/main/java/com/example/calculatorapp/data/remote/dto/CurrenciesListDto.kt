package com.example.calculatorapp.data.remote.dto

import com.squareup.moshi.Json

data class CurrenciesListDto(
    @field:Json(name = "results")
    val articles:List<CurrencyInfoDto>
)
