package com.chernovpavel.materialapp.domain

import com.chernovpavel.materialapp.api.MarsPhotoResponse
import com.chernovpavel.materialapp.api.PictureOfTheDayResponse

interface NasaRepository {

    suspend fun pictureOfTheDay(): PictureOfTheDayResponse
    suspend fun marsPhoto(): MarsPhotoResponse

}