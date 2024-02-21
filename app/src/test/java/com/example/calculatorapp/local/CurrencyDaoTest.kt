package com.example.calculatorapp.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.calculatorapp.data.local.CurrenciesDatabase
import com.example.calculatorapp.data.local.CurrencyDao
import com.example.calculatorapp.data.local.CurrencyInfoEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CurrencyDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: CurrenciesDatabase
    private lateinit var dao: CurrencyDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CurrenciesDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.currencyDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertItem() = runBlockingTest {
        val currencyInfoEntity = CurrencyInfoEntity(1,"USD",20.9)
        dao.insertCurrenciesListings(listOf(currencyInfoEntity))
        val allShoppingItems = dao.getCurrencyListings()
        assertThat(allShoppingItems).contains(currencyInfoEntity)
    }

    @Test
    fun deleteNews() = runBlockingTest {

        val currencyInfoEntity = CurrencyInfoEntity(1,"USD",20.9)
        dao.insertCurrenciesListings(listOf(currencyInfoEntity))
        dao.clearCurrencyListings()
        val allShoppingItems = dao.getCurrencyListings()
        assertThat(allShoppingItems.size).isEqualTo(0)
    }
}