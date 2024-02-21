package com.example.calculatorapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrenciesListings(
        stockListingsEntities: List<CurrencyInfoEntity>?
    )

    @Query("DELETE FROM currencyinfoentity")
    suspend fun clearCurrencyListings()

    @Query(
        """
            SELECT * 
            FROM currencyinfoentity
           
        """
    )
    suspend fun getCurrencyListings(): List<CurrencyInfoEntity>
}