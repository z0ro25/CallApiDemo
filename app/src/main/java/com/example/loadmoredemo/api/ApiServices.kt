package com.example.loadmoredemo.api

import com.example.loadmoredemo.ApiModel.Uniform
import retrofit2.Call
import retrofit2.http.GET


//https://youngkids-dev.acaziasoft.com/v1/api/public/products?categoryIds=12&page=0&size=8&sort=update_date:asc,quantity_sold:desc
interface ApiServices {
    @GET("v1/api/public/products?categoryIds=12&page=0&size=8&sort=update_date:asc,quantity_sold:desc")
    fun callApi() : Call<Uniform>
}