package com.chernovpavel.materialapp.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("planetary/apod")
    suspend fun pictureOfTheDay(
        @Query("api_key") apiKey: String
    ): PictureOfTheDayResponse

    @GET("mars-photos/api/v1/rovers/curiosity/photos?page=1")
    suspend fun marsPhoto(
        @Query("api_key") apiKey: String,
        @Query("earth_date") earthDate: String
    ): MarsPhotoResponse
}