package com.example.loadmoredemo.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiExacutor{
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
    private val okHttp = OkHttpClient.Builder().addInterceptor(logger)
    val BASE_URL = "https://youngkids-dev.acaziasoft.com/"
    val callapi  = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())
            .build()
            .create(ApiServices::class.java)
    }
