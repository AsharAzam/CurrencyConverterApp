package com.example.calculatorapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculatorapp.domain.repository.CurrenciesRepository
import com.example.calculatorapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CurrenciesRepository
) : ViewModel() {

    var state by mutableStateOf(CurrencyListingsState())

    init {
        getCurrencyListings()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Refresh -> {
                getCurrencyListings(true)
            }
        }
    }


    private fun getCurrencyListings(
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            repository
                .getCurrenciesListings(fetchFromRemote)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                state = state.copy(
                                    currencyList = listings
                                )
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }
}