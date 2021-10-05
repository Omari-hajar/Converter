package com.example.converter

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("eur.json")

    fun getData(): Call<Data?>?

}