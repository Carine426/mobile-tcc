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
import com.example.towersadmin.api.ApiService
import com.example.towersadmin.data.CadastroVisitanteReq
import com.example.towersadmin.data.VisitanteMoradorRes
import com.example.towersadmin.utils.SessionManager
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CadastroVisitanteActivity : AppCompatActivity() {

    lateinit var iv_image : ImageView

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    var imageBitmap: Bitmap? = null
    val CODE_IMAGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_visitante)

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
            abrirDashBoard()
        }

        tv_foto.setOnClickListener {
            abrirGaleria()
        }
        btn_salvar.setOnClickListener {

            if (et_nome.text.isNotEmpty() && et_cpf.text.isNotEmpty() && et_rg.text.isNotEmpty()) {
                apiClient.getApiService()
                    .visitanteMorador(
                        CadastroVisitanteReq(
                            et_nome.text.toString(), et_rg.text.toString(), et_cpf.text.toString(),
                            tv_foto.text.toString(),
                            dados.getInt("user_id", 0)
                        )
                    )
                    .enqueue(object : Callback<VisitanteMoradorRes> {
                        override fun onResponse(
                            call: Call<VisitanteMoradorRes>,
                            response: Response<VisitanteMoradorRes>
                        ) {
                            val visitanteMoradorRes = response.body()

                            if (visitanteMoradorRes?.morador?.id != null) {
                                Log.i("response", visitanteMoradorRes.toString())
                                msgSucesso()
                            }
                        }

                        override fun onFailure(call: Call<VisitanteMoradorRes>, t: Throwable) {
                            msgErro()
                        }

                    })
            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_LONG).show()
            }

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

        if (requestCode == CODE_IMAGE && resultCode == -1){

            //recuperar a imagem no stream
            val stream = contentResolver.openInputStream(data!!.data!!)

            //Trnaformar Stream num BitMap

            imageBitmap = BitmapFactory.decodeStream(stream)

            //Colocar imagem no ImageView
            iv_image.setImageBitmap(imageBitmap)

        }
        else {
            Toast.makeText(this, "Selecione uma foto", Toast.LENGTH_LONG).show()
        }
    }
}