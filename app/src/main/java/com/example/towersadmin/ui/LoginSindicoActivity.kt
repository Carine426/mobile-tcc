package com.example.towersadmin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.towersadmin.R
import com.example.towersadmin.api.ApiClient
import com.example.towersadmin.data.LoginRequest
import com.example.towersadmin.data.LoginResponse
import com.example.towersadmin.data.LoginSindicoResponse
import com.example.towersadmin.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginSindicoActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_sindico)

        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        val et_email: EditText = findViewById(R.id.et_email)
        val et_senha: EditText = findViewById(R.id.et_senha)
        val btn_continuar: Button = findViewById(R.id.btn_continuar)
        val tv_erro: TextView = findViewById(R.id.tv_mensagem_de_erro)
        val tv_esqueceu_senha: TextView = findViewById(R.id.esqueceu_senha)

        fun abrirDashBoard() {
            val intent = Intent(this, DashBoardActivity::class.java)
            startActivity(intent)
        }


        btn_continuar.setOnClickListener {
            apiClient.getApiService()
                    .loginSindico(LoginRequest(et_email.text.toString(), et_senha.text.toString()))
                    .enqueue(object : Callback<LoginSindicoResponse> {

                        override fun onResponse(
                                call: Call<LoginSindicoResponse>, response: Response<LoginSindicoResponse>
                        ) {
                            val loginResponse = response.body()

                            if (loginResponse?.sindicoId != null) {
                                sessionManager.saveAuthToken(loginResponse.sindicoId, loginResponse.name,
                                        loginResponse.surname, loginResponse.cpf,
                                        loginResponse.birth, loginResponse.email,
                                        loginResponse.token)

                                abrirDashBoard()

                                Log.i("response", response.body().toString())

                            } else {
                                tv_erro.setText("Email ou senha incorretos!")

                            }
                        }

                        override fun onFailure(call: Call<LoginSindicoResponse>, t: Throwable) {
                            tv_erro.setText("Algo deu errado!")
                        }
                    })

        }

        tv_esqueceu_senha.setOnClickListener {
            Toast.makeText(applicationContext, "Contacte a Administração do seu condominio!", Toast.LENGTH_LONG).show()
        }

    }

}
