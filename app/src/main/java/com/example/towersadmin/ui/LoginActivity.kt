package com.example.towersadmin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.towersadmin.R
import com.example.towersadmin.api.ApiClient
import com.example.towersadmin.data.LoginRequest
import com.example.towersadmin.data.LoginResponse
import com.example.towersadmin.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log


class LoginActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val dados = getSharedPreferences("TowersAdmin", MODE_PRIVATE)

        val lembrar = dados.getBoolean("lembrar", false)

        if (lembrar == true){
            abrirDashBoard()
        }

        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        val et_email: EditText = findViewById(R.id.et_email)
        val et_senha: EditText = findViewById(R.id.et_senha)
        val btn_continuar: Button = findViewById(R.id.btn_continuar)
        val tv_erro: TextView = findViewById(R.id.tv_mensagem_de_erro)
        val tv_esqueceu_senha: TextView = findViewById(R.id.esqueceu_senha)
        val check_lembrar : CheckBox = findViewById(R.id.check_lembrar)


        btn_continuar.setOnClickListener {

            apiClient.getApiService()
                    .login(LoginRequest(et_email.text.toString(), et_senha.text.toString()))
                    .enqueue(object : Callback<LoginResponse> {

                        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                            val loginResponse = response.body()

                            if (loginResponse?.moradorId != null && check_lembrar.isChecked) {
                                sessionManager.saveAuthToken(
                                        loginResponse.moradorId, loginResponse.name,
                                        loginResponse.surname, loginResponse.cpf,
                                        loginResponse.birth, loginResponse.email,
                                        loginResponse.token, lembrar = true
                                )

                                Log.i("response", loginResponse.toString())
                                abrirDashBoard()

                            } else if(loginResponse?.moradorId != null) {
                                sessionManager.saveAuthToken(
                                    loginResponse.moradorId, loginResponse.name,
                                    loginResponse.surname, loginResponse.cpf,
                                    loginResponse.birth, loginResponse.email,
                                    loginResponse.token, lembrar = false
                                )

                                Log.i("response", loginResponse.toString())
                                abrirDashBoard()

                            } else{
                                tv_erro.setText("Email ou senha incorretos!")
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            tv_erro.setText("Algo deu errado!")
                        }
                    })

        }

        tv_esqueceu_senha.setOnClickListener {
            Toast.makeText(applicationContext, "Contacte a Administra????o do seu condominio!", Toast.LENGTH_LONG).show()
        }

    }
    private fun abrirDashBoard() {
        val intent = Intent(this, DashBoardActivity::class.java)
        startActivity(intent)
        finish()
    }

}
