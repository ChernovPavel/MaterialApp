package com.example.materialapp.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.materialapp.BuildConfig
import com.example.materialapp.api.MarsPhotoResponse
import com.example.materialapp.api.NasaApi
import com.example.materialapp.api.PictureOfTheDayResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun marsPhoto(): MarsPhotoResponse {

        val formatter = DateTimeFormatter.ofPattern("yyyy-M-dd")
        val threeYearsAgo = LocalDateTime.now().minusYears(3).format(formatter)

        return api.marsPhoto(BuildConfig.NASA_API_KEY, threeYearsAgo)
    }
}