package com.example.towersadmin.api

import com.example.towersadmin.utils.Constants
import com.example.towersadmin.utils.Constants.BASE_URL
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit instance class
 */
class ApiClient {
    private lateinit var apiService: ApiService

    fun getApiService(): ApiService {

        // Initialize ApiService if not initialized yet
        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }

        return apiService
    }


}
