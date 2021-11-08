package com.example.towersadmin.data

import com.google.gson.annotations.SerializedName

class LoginResponsee {

    data class Token(

        @SerializedName("token")
        var token: String,
    )

    data class Sindico(
        @SerializedName("Id")
        var Id: Int,

        @SerializedName("name")
        var name: String,

        @SerializedName("surname")
        var surname: String,

        @SerializedName("cpf")
        var cpf: String,

        @SerializedName("birth")
        var birth: String,

        @SerializedName("email")
        var email: String,

        )

}