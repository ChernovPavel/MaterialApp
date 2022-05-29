package com.chernovpavel.materialapp.ui.picOfDay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chernovpavel.materialapp.R
import com.chernovpavel.materialapp.api.PictureOfTheDayResponse
import com.chernovpavel.materialapp.domain.NasaRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(private val repository: NasaRepository) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _response: MutableStateFlow<PictureOfTheDayResponse?> = MutableStateFlow(null)
    val response = _response.asStateFlow()

    private val _error: MutableSharedFlow<String?> = MutableSharedFlow()
    val error = _error.asSharedFlow()


    fun requestPictureOfTheDay() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.pictureOfTheDay()
                _response.emit(response)

            } catch (exp: IOException) {
                _error.emit(R.string.network_error.toString())
            }

            _loading.emit(false)
        }
    }
}

class MainViewModelFactory(private val repository: NasaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(repository) as T
}