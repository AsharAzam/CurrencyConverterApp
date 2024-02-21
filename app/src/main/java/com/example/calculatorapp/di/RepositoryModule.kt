package com.example.calculatorapp.di

import com.example.calculatorapp.data.repository.CurrenciesRepositoryImpl
import com.example.calculatorapp.domain.repository.CurrenciesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCurrencyRepository(
        currenciesRepositoryImpl: CurrenciesRepositoryImpl
    ): CurrenciesRepository

}