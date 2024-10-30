package com.hu.picit.app.network

import com.hu.picit.app.model.ApiResponse
import com.hu.picit.app.model.CategoryAttributes
import com.hu.picit.app.model.FruitAttributes
import com.hu.picit.app.model.LocationAttributes
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://10.0.2.2:8080/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Creating Retrofit instance
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface ApiService {
    @GET("locations")
    suspend fun getLocations(): ApiResponse<LocationAttributes>

    @GET("categories")
    suspend fun getCategories(): ApiResponse<CategoryAttributes>

    @GET("fruits")
    suspend fun getFruits(): ApiResponse<FruitAttributes>

    @GET("fruits/recommended")
    suspend fun getRecommendedFruits(): ApiResponse<FruitAttributes>
}

// Using lazy delegation to create the retrofitService instance
object ApiServiceProvider {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}