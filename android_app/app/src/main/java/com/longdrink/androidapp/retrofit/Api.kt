package com.longdrink.androidapp.retrofit

import com.longdrink.androidapp.api.ApiService

object Api {

    val BASE_URL = "http://10.0.2.2:8080/api/v1/"

    val apiService : ApiService = RetrofitClient
        .getConnection(BASE_URL)
        .create(ApiService::class.java)
}