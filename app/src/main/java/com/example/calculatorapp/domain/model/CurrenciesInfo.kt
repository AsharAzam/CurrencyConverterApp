package com.example.calculatorapp.domain.model

data class CurrenciesInfo(
     val id:Int?,
     val title: String?,
     val rate: Double?,
     )

data class Rates(
     val success: Boolean,
     val timestamp: Long,
     val base: String,
     val date: String,
     val rates: Map<String, Double>
)
