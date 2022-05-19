package com.example.materialapp.domain

import com.example.materialapp.BuildConfig
import com.example.materialapp.api.MarsPhotoResponse
import com.example.materialapp.api.NasaApi
import com.example.materialapp.api.PictureOfTheDayResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NasaRepositoryImpl : NasaRepository {

    private val api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.nasa.gov/")
        .client(
            OkHttpClient.Builder().apply {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }.build()
        )
        .build()
        .create(NasaApi::class.java)

    override suspend fun pictureOfTheDay(): PictureOfTheDayResponse =
        api.pictureOfTheDay(BuildConfig.NASA_API_KEY)

    override suspend fun marsPhoto(): MarsPhotoResponse =
        api.marsPhoto(BuildConfig.NASA_API_KEY)
}