package com.example.towersadmin.api

import com.example.towersadmin.data.LoginRequest
import com.example.towersadmin.data.LoginResponse
import com.example.towersadmin.data.LoginResponsee
import com.example.towersadmin.data.LoginSindicoResponse
import com.example.towersadmin.utils.Constants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST(Constants.LOGIN_URL)
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST(Constants.SINDICO_URL)
    fun loginSindico(@Body loginRequest: LoginRequest): Call<LoginResponse>
}