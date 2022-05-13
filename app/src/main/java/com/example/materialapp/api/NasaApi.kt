package com.example.materialapp.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("planetary/apod")
    suspend fun pictureOfTheDay(
        @Query("api_key") apiKey: String
    ): PictureOfTheDayResponse
}