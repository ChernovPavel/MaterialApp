package com.chernovpavel.materialapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chernovpavel.materialapp.R
import com.chernovpavel.materialapp.api.MarsPhotoResponse
import com.chernovpavel.materialapp.domain.NasaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MarsPhotoViewModel(private val repository: NasaRepository) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    private val _response: MutableStateFlow<MarsPhotoResponse?> = MutableStateFlow(null)
    val response: Flow<MarsPhotoResponse?> = _response

    private val _error: MutableSharedFlow<String?> = MutableSharedFlow()
    val error: Flow<String?> = _error


    fun requestMarsPhoto() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.marsPhoto()
                _response.emit(response)

            } catch (exp: IOException) {
                _error.emit(R.string.network_error.toString())
            }

            _loading.emit(false)
        }
    }
}

class MarsPhotoViewModelFactory(private val repository: NasaRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MarsPhotoViewModel(repository) as T
}