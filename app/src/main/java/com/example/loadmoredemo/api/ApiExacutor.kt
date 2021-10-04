package com.example.loadmoredemo.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiExacutor{
    private val logBody = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val logHeaders = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
    private val okHttp = OkHttpClient.Builder().addInterceptor(logBody)
    val BASE_URL = "https://youngkids-dev.acaziasoft.com/"
    val callapi  = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())
            .build()
            .create(ApiServices::class.java)
    }
