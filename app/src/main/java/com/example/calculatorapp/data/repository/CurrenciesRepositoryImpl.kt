package com.example.calculatorapp.data.repository

import com.example.calculatorapp.data.local.CurrenciesDatabase
import com.example.calculatorapp.data.local.CurrencyInfoEntity
import com.example.calculatorapp.data.mapper.toCurrencyInfo
import com.example.calculatorapp.data.remote.CurrenciesApi
import com.example.calculatorapp.domain.model.CurrenciesInfo
import com.example.calculatorapp.domain.repository.CurrenciesRepository
import com.example.calculatorapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrenciesRepositoryImpl @Inject constructor(
    private val api: CurrenciesApi,
    db: CurrenciesDatabase,
) : CurrenciesRepository {
    private val dao = db.currencyDao

    override suspend fun getCurrenciesListings(fetchFromRemote: Boolean): Flow<Resource<List<CurrenciesInfo>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.getCurrencyListings()
            emit(Resource.Success(
                data = localListings.map {
                    it.toCurrencyInfo()
                }
            ))
            val shouldJustLoadFromCache = localListings.isNotEmpty() && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                api.getCurrencyList()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings.let {
                if (it != null) {
                    val list = mutableListOf<String>()
                    for (keys in it.keys) {
                        list.add(keys)
                    }
                    println(list)
                    val commaSeparatedString = list.joinToString(",")

                    val ratesList = try {
                        api.getRates(symbols = commaSeparatedString)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        emit(Resource.Error("Couldn't load data"))
                        null
                    } catch (e: HttpException) {
                        e.printStackTrace()
                        emit(Resource.Error("Couldn't load data"))
                        null
                    }
                    ratesList?.rates!!.let { listings ->
                        val list: MutableList<CurrencyInfoEntity> =
                            mutableListOf<CurrencyInfoEntity>()
                        dao.clearCurrencyListings()
                        listings.entries.forEachIndexed { index, entry ->
                            val (key, value) = entry
                            println("Index: $index, Key: $key, Value: $value")
                            list.add(CurrencyInfoEntity(index, key, value))
                        }
                        dao.insertCurrenciesListings(
                            list
                        )
                        emit(
                            Resource.Success(
                                data = dao
                                    .getCurrencyListings()
                                    .map { it.toCurrencyInfo() }
                            ))
                        emit(Resource.Loading(false))
                    }
                }
            }
        }
    }
}

