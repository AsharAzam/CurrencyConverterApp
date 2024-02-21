package com.example.calculatorapp.data.mapper

import com.example.calculatorapp.data.local.CurrencyInfoEntity
import com.example.calculatorapp.data.remote.dto.CurrencyInfoDto
import com.example.calculatorapp.domain.model.CurrenciesInfo


fun CurrencyInfoEntity.toCurrencyInfo(): CurrenciesInfo {
    return CurrenciesInfo(
        id= id,
        title = title ?: "",
        rate = rate ?: 0.0,
    )
}
fun CurrencyInfoDto.toCurrencyInfoEntity(): CurrencyInfoEntity {

    return CurrencyInfoEntity(
        id= id,
        title = title ?: "",
        rate  = rate ?: 0.0,
    )
}
fun CurrencyInfoDto.toCurrencyInfo(): CurrenciesInfo {

    return CurrenciesInfo(
        id= id,
        title = title ?: "",
        rate = rate ?: 0.0,
    )
}