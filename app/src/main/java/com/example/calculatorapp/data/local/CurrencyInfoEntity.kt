package com.example.calculatorapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class CurrencyInfoEntity(
    @PrimaryKey
    val id: Int? = null,
    val title: String?,
    val rate: Double?,
)

