package com.example.towersadmin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.towersadmin.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val acesso_morador : Button = findViewById(R.id.acesso_morador)
        val acesso_sindico : Button = findViewById(R.id.acesso_sindico)

        acesso_morador.setOnClickListener {
            val abrirLoginMorador = Intent(this, LoginActivity::class.java)
            startActivity(abrirLoginMorador)
        }

        acesso_sindico.setOnClickListener {
            val abrirLoginSindico =  Intent(this, LoginSindicoActivity::class.java)
            startActivity(abrirLoginSindico)

        }
    }
}