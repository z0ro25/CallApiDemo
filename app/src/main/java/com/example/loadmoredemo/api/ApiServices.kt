package com.example.loadmoredemo.api

import com.example.loadmoredemo.ApiModel.Uniform
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


//https://youngkids-dev.acaziasoft.com/v1/api/public/products?page=0&size=8&sort=update_date:asc,quantity_sold:desc
interface ApiServices {
    //todo use query
    @GET("v1/api/public/products")
    fun callApi(@Query("page") page:Int ,
                @Query("size") size:Int,
                @Query("sort") sort:String,
    ) : Call<Uniform>

    @GET("v1/api/public/products/")
    fun callId(@Query("id") id:String,

    ) : Call<Uniform>
}