package com.example.calculatorapp.presentation

import com.example.calculatorapp.domain.model.CurrenciesInfo

data class CurrencyListingsState(
    val currencyList: List<CurrenciesInfo> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)
