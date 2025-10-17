package com.android.example.quotesloader.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.quotesloader.data.repository.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


sealed class UiState {
    object Loading : UiState()
    data class Success(val quotes: List<String>) : UiState()
    data class Error(val message: String) : UiState()
}

class QuoteViewModel : ViewModel(){
    private val quoteRepository = QuoteRepository()
    private val _uiState = mutableStateOf<UiState>(UiState.Loading)
    val uiState = _uiState

    fun loadQuotes() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = withContext(Dispatchers.IO) {
                    quoteRepository.fetchQuotes()
                }
                _uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}