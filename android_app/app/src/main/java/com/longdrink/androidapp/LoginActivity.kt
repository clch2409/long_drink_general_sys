package com.longdrink.androidapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
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
            val username = binding.txtUsuario.text.toString()
            val password = binding.txtContrasena.text.toString()
            if (checkingEmpty(username, password))
            {
                showSnackbar("Ingrese todos los datos solicitados, por favor")
            }
            else if (!checkUsernameFormat(username))
            {
                showSnackbar("Ingrese el formato correcto del usuario, por favor\"")
            }
            else
            {
                doLogin(LoginSendData(username, password))
            }
        }

        binding.register.setOnClickListener { goToRegister() }
        binding.forgetPass.setOnClickListener { goToForgot() }

        setContentView(binding.root)
    }

    private fun doLogin(sendData : LoginSendData){

        CoroutineScope(Dispatchers.IO).launch{
            val response : Response<LoginWebResponse>  =
                retrofit.create(ApiService::class.java).IniciarSesion(sendData)

            if (response.code() == 401){
                 showSnackbar("Sus datos no coinciden, intente de nuevo")
            }
            else {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun checkingEmpty(username: String, password: String) : Boolean{
        if (username.isEmpty() || password.isEmpty()){
            return true
        }
        return false
    }

    private fun checkUsernameFormat(username: String) : Boolean{
        return username.matches("^[A-Z]{3}\\d{8}$".toRegex())
    }

    private fun showSnackbar(text : String){
        this.runOnUiThread { Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show() }
    }

    private fun goToRegister(){
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun goToForgot(){
        showSnackbar("Estamos trabajando en ello \uD83D\uDEE0")
    }

    private fun getRetrofit() : Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }
}