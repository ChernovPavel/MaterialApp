package com.example.materialapp.domain

import com.example.materialapp.api.MarsPhotoResponse
import com.example.materialapp.api.PictureOfTheDayResponse

interface NasaRepository {

    suspend fun pictureOfTheDay(): PictureOfTheDayResponse
    suspend fun marsPhoto(): MarsPhotoResponse

}