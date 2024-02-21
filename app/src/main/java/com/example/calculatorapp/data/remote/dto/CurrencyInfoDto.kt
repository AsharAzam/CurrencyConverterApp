package com.example.calculatorapp.data.remote.dto

import com.squareup.moshi.Json

data class CurrencyInfoDto(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "rate") val rate: Double?,
)
