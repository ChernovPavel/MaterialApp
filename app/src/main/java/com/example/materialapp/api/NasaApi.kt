package com.example.materialapp.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("planetary/apod")
    suspend fun pictureOfTheDay(
        @Query("api_key") apiKey: String
    ): PictureOfTheDayResponse

    @GET("mars-photos/api/v1/rovers/curiosity/photos?earth_date=2015-6-3&page=1")
    suspend fun marsPhoto(
        @Query("api_key") apiKey: String,
//        @Query("earth_date") earthDate: String
    ): MarsPhotoResponse
}