package com.example.materialapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.materialapp.api.PictureOfTheDayResponse
import com.example.materialapp.domain.NasaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(private val repository: NasaRepository) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    private val _response: MutableStateFlow<PictureOfTheDayResponse?> = MutableStateFlow(null)
    val response: Flow<PictureOfTheDayResponse?> = _response

    private val _error: MutableSharedFlow<String?> = MutableSharedFlow()
    val error: Flow<String?> = _error


    fun requestPictureOfTheDay() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.pictureOfTheDay()
                _response.emit(response)

            } catch (exp: IOException) {
                _error.emit("Network error")
            }

            _loading.emit(false)
        }
    }
}

class MainViewModelFactory(private val repository: NasaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(repository) as T
}