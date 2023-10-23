package com.longdrink.androidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.longdrink.androidapp.api.ApiService
import com.longdrink.androidapp.databinding.ActivityLoginBinding
import com.longdrink.androidapp.model.LoginSendData
import com.longdrink.androidapp.model.LoginWebResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var retrofit : Retrofit
    private val BASE_URL: String = "http://10.0.2.2:8080/api/v1/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        retrofit = getRetrofit()

        binding.btnIniciar.setOnClickListener {
            doLogin(getInfo())
        }

        setContentView(binding.root)
    }

    private fun getInfo(): LoginSendData {
        val usuario: String = binding.txtUsuario.text.toString()
        val contrasena = binding.txtContrasena.text.toString()
        return LoginSendData(usuario, contrasena)
    }

    private fun doLogin(sendData : LoginSendData){
        CoroutineScope(Dispatchers.IO).launch{
            val response : Response<LoginWebResponse>  =
                retrofit.create(ApiService::class.java).IniciarSesion(sendData)

            if (response.code() == 401){
                Log.i("ERROR", "No hay inicio de sesión")
            }
            else{
                Log.i("SUCCESS", response.body().toString());
            }
        }
    }

    private fun getRetrofit() : Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }
}