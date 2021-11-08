package com.example.towersadmin.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.towersadmin.R

class SessionManager(context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_ID = "user_id"
        const val USER_TOKEN = "user_token"
    }

    //Função para salvar o token de autenticação
    fun saveAuthToken( id:Int, token:String){
    val editor = prefs.edit()
        editor.putInt(USER_ID, id)
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    //Função Fetch do token de autenticação

    fun fetchAuthToken(): String?{
        return prefs.getString(USER_TOKEN, null)
    }


}