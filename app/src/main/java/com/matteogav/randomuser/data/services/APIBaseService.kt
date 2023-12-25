package com.matteogav.randomuser.data.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIBaseService {

    private const val BASE_URL = "https://randomuser.me/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }
}