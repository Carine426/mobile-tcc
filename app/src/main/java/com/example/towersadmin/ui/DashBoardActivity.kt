package com.example.towersadmin.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle;
import android.preference.PreferenceManager
import android.view.MenuItem;
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.towersadmin.R
import com.google.android.material.navigation.NavigationView;


class DashBoardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView : NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val header: View = navView.getHeaderView(0)
        val header_nome:TextView = header.findViewById(R.id.header_nome)
        val header_email:TextView = header.findViewById(R.id.header_email)


        val dados = getSharedPreferences("TowersAdmin", MODE_PRIVATE)

        val nome_header = dados.getString("user_name", "")
        val email_header = dados.getString("user_email", "")

        header_nome.text = nome_header
        header_email.text = email_header

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )



        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(
            this
        )
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                Toast.makeText(this, "Home Clicado", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_agendamento -> {
                val intent = Intent(this, AgendamentoEspaco::class.java)
                startActivity(intent)
            }
            R.id.nav_conversas -> {
                Toast.makeText(this, "Conversas Clicado", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_pagamento_condominal -> {
                Toast.makeText(this, "Tá Deveno Hein!", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_assembleias -> {
                Toast.makeText(this, "Assembleias Clicado", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_votacao -> {
                val intent = Intent(this, activity_votacao::class.java)
                startActivity(intent)
            }
            R.id.nav_aviso -> {
                val intent = Intent(this, activity_reclamacoes::class.java)
                startActivity(intent)
            }
            R.id.nav_mural_de_avisos -> {
                val intent = Intent(this, activity_aviso::class.java)
                startActivity(intent)
            }
            R.id.nav_cadastro_visitantes -> {
                val intent = Intent(this, CadastroVisitanteActivity::class.java)
                startActivity(intent)
            }
        }
            drawerLayout.closeDrawer(GravityCompat.START)
                    return true

    }

}


