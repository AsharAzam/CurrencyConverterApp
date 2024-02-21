package com.example.calculatorapp.di

import android.app.Application
import androidx.room.Room
import com.example.calculatorapp.data.local.CurrenciesDatabase
import com.example.calculatorapp.data.remote.CurrenciesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): CurrenciesApi {
        return Retrofit.Builder()
            .baseUrl(CurrenciesApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }).build())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): CurrenciesDatabase {
        return Room.databaseBuilder(
            app,
            CurrenciesDatabase::class.java,
            "movies.db"
        ).build()
    }
}