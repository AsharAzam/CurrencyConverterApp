package com.example.calculatorapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CurrencyInfoEntity::class],
    version = 3
)
abstract class CurrenciesDatabase: RoomDatabase() {
    abstract val currencyDao: CurrencyDao
}