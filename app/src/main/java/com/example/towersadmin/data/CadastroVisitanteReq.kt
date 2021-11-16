package com.example.towersadmin.data

import com.google.gson.annotations.SerializedName
import retrofit2.http.Part

data class CadastroVisitanteReq(

        @Part("name")
        var name: String,

        @Part("rg")
        var rg: String,

        @Part("cpf")
        var cpf: String,

        @Part("image")
        var image: String,

        @Part("morador_id")
        var morador_id: Int
)
