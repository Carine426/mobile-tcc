package com.example.towersadmin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.towersadmin.R


class CadastroVisitanteActivity : AppCompatActivity() {

    lateinit var iv_voltar : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_visitante)

        val iv_voltar: Button = findViewById(R.id.iv_voltar)

        fun abrirDashBoard() {
            val intent = Intent(this, DashBoardActivity::class.java)
            startActivity(intent)
        }

        iv_voltar.setOnClickListener {
            abrirDashBoard()
        }


    }
}