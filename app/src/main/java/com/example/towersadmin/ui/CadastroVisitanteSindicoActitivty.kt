package com.example.towersadmin.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.towersadmin.R
import com.example.towersadmin.api.ApiClient
import com.example.towersadmin.data.CadastroVisitanteSindicoReq
import com.example.towersadmin.data.VisitanteSindicoRes
import com.example.towersadmin.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CadastroVisitanteSindicoActitivty : AppCompatActivity() {

    lateinit var iv_image: ImageView

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    var imageBitmap: Bitmap? = null
    val CODE_IMAGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_visitante_sindico_actitivty)

        val dados = getSharedPreferences("TowersAdmin", MODE_PRIVATE)
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        iv_image = findViewById(R.id.iv_image)

        val iv_voltar: Button = findViewById(R.id.iv_voltar)
        val btn_salvar: Button = findViewById(R.id.btn_salvar)
        val et_nome: EditText = findViewById(R.id.et_nome)
        val et_rg: EditText = findViewById(R.id.et_rg)
        val et_cpf: EditText = findViewById(R.id.et_cpf)
        val tv_foto: TextView = findViewById(R.id.tv_foto)

        fun abrirDashBoard() {
            val intent = Intent(this, DashBoardActivity::class.java)
            startActivity(intent)
        }

        iv_voltar.setOnClickListener {
            abrirDashBoardSindico()
        }

        tv_foto.setOnClickListener {
            abrirGaleria()
        }

        btn_salvar.setOnClickListener {

            apiClient.getApiService()
                    .visitanteSindico(CadastroVisitanteSindicoReq(et_nome.text.toString(),
                            et_rg.text.toString(), et_cpf.text.toString(), tv_foto.text.toString(),
                            dados.getInt("user_id", 0)))
                    .enqueue(object : Callback<VisitanteSindicoRes> {
                        override fun onResponse(call: Call<VisitanteSindicoRes>, response: Response<VisitanteSindicoRes>) {
                            val visitanteSindicoRes = response.body()

                            if (visitanteSindicoRes?.sindico?.id != null) {
                                Log.i("response", visitanteSindicoRes.toString())
                                msgSucesso()
                            }
                        }

                        override fun onFailure(call: Call<VisitanteSindicoRes>, t: Throwable) {
                            msgErro()
                        }

                    })

        }


    }

    private fun abrirGaleria() {

        // Chamando a galeria de imagens

        val intent = Intent(Intent.ACTION_GET_CONTENT)

        // Definindo qual o tipo de conteúdo deverá ser obtido

        intent.type = "image/*"

        // Iniciar a Activity, mas nesse caso nós queremos que essa activity retorne algo pra gnt, a imagem

        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Escolha uma foto"
                ),
                CODE_IMAGE
        )
    }

    private fun msgErro() {
        Toast.makeText(this, "Ocorreu um erro, tente novamente mais tarde!", Toast.LENGTH_LONG).show()
    }
    private fun msgSucesso(){
        Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_IMAGE && resultCode == -1) {

            //recuperar a imagem no stream
            val stream = contentResolver.openInputStream(data!!.data!!)

            //Trnaformar Stream num BitMap

            imageBitmap = BitmapFactory.decodeStream(stream)

            //Colocar imagem no ImageView
            iv_image.setImageBitmap(imageBitmap)

        } else {
            Toast.makeText(this, "Selecione uma foto", Toast.LENGTH_LONG).show()
        }
    }
    private fun abrirDashBoardSindico() {
        val intent = Intent(this, DashBoardSindicoActivity::class.java)
        startActivity(intent)
        finish()
    }
}