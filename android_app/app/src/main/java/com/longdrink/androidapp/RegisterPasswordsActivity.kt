package com.longdrink.androidapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.longdrink.androidapp.api.ApiService
import com.longdrink.androidapp.databinding.ActivityRegisterPasswordsBinding
import com.longdrink.androidapp.model.RegisterResponse
import com.longdrink.androidapp.model.RegisterSendData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterPasswordsActivity : AppCompatActivity() {
    private val BASE_URL = "http://10.0.2.2:8080/api/v1/"
    private lateinit var retrofit: Retrofit
    private lateinit var binding : ActivityRegisterPasswordsBinding
    private lateinit var sendData : RegisterSendData
    private lateinit var recivedData : RegisterSendData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Decirle a Cristian que al momento del registro haga un envío del nombre de usuario del alumno*/
        /** Para poder mostrarlo en ventana emergente*/
        recivedData = RegisterSendData(intent.getStringExtra("nombre").toString(),
            intent.getStringExtra("apa").toString(),
            intent.getStringExtra("ama").toString(),
            intent.getStringExtra("dni").toString(),
            intent.getStringExtra("phone").toString(),
            intent.getStringExtra("email").toString(),
            "")

        binding = ActivityRegisterPasswordsBinding.inflate(layoutInflater)
        retrofit = getRetrofit()


        binding.backLogin.setOnClickListener {
            showingMessageAndSending("Regresar a Login","¿Desea volver a la pantalla de Login? Perderá todo el progeso", 0)
        }


        binding.backRegister.setOnClickListener{
            showingMessageAndSending("Regresar a Registro","¿Desea volver a la pantalla anterior? Deberá ingresar los datos de nuevo", 1)
        }

        binding.buttonRegister.setOnClickListener {
            if (checkEmpty()){
                showSnackbar("Ingrese las dos contraseñas para poder continuar")
            }
            else if (!checkEquals()){
                showSnackbar("Sus contraseñas no coinciden, por favor verifíquelas")
            }
            else{
                showingMessageAndSending("Terminar registro", "¿Dese continuar con esta información registrada?" +
                        "\nNombres: " + recivedData.nombre +
                        "\nApellido Paterno: " + recivedData.apPaterno +
                        "\nApellido Materno: " + recivedData.apMaterno +
                        "\nDNI: " + recivedData.dni +
                        "\nTeléfono: " + recivedData.telefono +
                        "\nEmail: " + recivedData.email, 2)
            }
        }

        setContentView(binding.root)
    }

    private fun showingMessageAndSending(titulo: String, mensaje : String, cualFuncion : Int){
        val mensajeEmergente = AlertDialog.Builder(this)

        mensajeEmergente.apply {
            setTitle(titulo)
            setMessage(mensaje)
            setPositiveButton("SI") { _: DialogInterface?, _: Int ->
                if (cualFuncion == 0){
                    goToLogin()
                }
                else if (cualFuncion == 1){
                    goToRegister()
                }
                else if (cualFuncion == 2){
                    registerStudent(getData())
                }
            }
            setNegativeButton("NO"){_: DialogInterface?, _: Int ->

            }
        }

        mensajeEmergente.create()
        mensajeEmergente.show()
    }

    private fun getData() : RegisterSendData{
        sendData = RegisterSendData(
            recivedData.nombre,
            recivedData.apPaterno,
            recivedData.apMaterno,
            recivedData.dni,
            recivedData.telefono,
            recivedData.email,
            binding.txtPass.text.toString()
        )
        return sendData
    }

    private fun registerStudent(registerData : RegisterSendData) {
        CoroutineScope(Dispatchers.IO).launch {
            val response : Response<RegisterResponse> =
                retrofit.create(ApiService::class.java).registroAlumno(registerData)

            if (response.body()?.estado?.toInt() == 400){
                showSnackbar(response.body()?.mensaje.toString())
            }
            else if (response.body()?.estado?.toInt() == 500){
                showSnackbar(response.body()?.mensaje.toString())
            }
            else{
                showingMessageAndSending("Registro Exitoso", "Para poder ingresar al sistema, le hemos creado un nombre de usuario" +
                        " el cual es el siguiente: ${response.body()}. Tome nota, lo necesitará para " +
                        "ingresar al sistema",0)
            }
        }
    }


    private fun checkEmpty(): Boolean{
        return binding.txtPass.text.isEmpty()
                || binding.txtPassRepeat.text.isEmpty()
    }

    private fun checkEquals() : Boolean{
        return binding.txtPass.text.toString() == binding.txtPassRepeat.text.toString()
    }

    private fun goToLogin() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToRegister(){
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showSnackbar(text : String){
        this.runOnUiThread { Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show() }
    }

    private fun getRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}