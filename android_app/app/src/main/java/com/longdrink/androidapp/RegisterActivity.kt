package com.longdrink.androidapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.longdrink.androidapp.databinding.ActivityRegisterBinding
import com.longdrink.androidapp.model.RegisterSendData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegisterBinding
    lateinit var retrofit: Retrofit
    val BASE_URL : String = "http://10.0.2.2:8080/api/v1/"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        retrofit = getRetrofit()

        binding.buttonContinueRegister.setOnClickListener {
            if (checkEmpty()){
                showSnackbar("Ingrese TODOS los datos solicitados para pode continuar")
                changeColors()
            }
            else if (!checkDniFormat()){
                showSnackbar("Recuerde que el DNI deben ser 8 dígitos numéricos")
                changeDniColors()
            }
            else if (!checkPhoneFormat()){
                showSnackbar("Recuerde que el telefono debe tener nueve número y comenzar con el número 9")
                changeTelfColors()
            }
            else if (!checkNamesFormat()){
                showSnackbar("Ingrese los datos correctos en sus nombres y apellidos")
            }
            else{
                continueRegister(getData())
            }
        }

        binding.backRegister.setOnClickListener {
            goToLogin()
        }

        setContentView(R.layout.activity_register)
    }
    /*-------Register Functions---------*/
    private fun getData() : RegisterSendData{
        val nombre = binding.txtNombres.text.toString()
        val apa = binding.txtApPaterno.text.toString()
        val ama = binding.txtApMaterno.text.toString()
        val email = binding.txtEmail.text.toString()
        val dni = binding.txtDni.text.toString()
        val telf = binding.txtTelf.text.toString()
        return RegisterSendData(nombre, apa, ama, dni, telf, email, "")
    }

    private fun continueRegister(registerData : RegisterSendData) {
        val intent = Intent(applicationContext, RegisterPasswordsActivity::class.java).apply {
            putExtra("nombre", registerData.nombre)
            putExtra("apa", registerData.apPaterno)
            putExtra("ama", registerData.apMaterno)
            putExtra("dni", registerData.dni)
            putExtra("phone", registerData.telefono)
            putExtra("email", registerData.email)
        }

        startActivity(intent)
    }

    /*-------Check Functions---------*/
    private fun checkEmpty(): Boolean{
        return binding.txtNombres.text.isEmpty() ||
                binding.txtApPaterno.text.isEmpty() ||
                binding.txtApMaterno.text.isEmpty() ||
                binding.txtDni.text.isEmpty() ||
                binding.txtTelf.text.isEmpty() ||
                binding.txtEmail.text.isEmpty()
    }

    private fun checkNamesFormat(): Boolean{
        return binding.txtNombres.toString().matches("^[A-Za-záéíóú]+ *[A-Za-záéíóú]*$".toRegex())
                || binding.txtApPaterno.toString().matches("^[A-Za-záéíóú]+ *[A-Za-záéíóú]*$".toRegex())
                || binding.txtApMaterno.toString().matches("^[A-Za-záéíóú]+ *[A-Za-záéíóú]*$".toRegex())
    }

    private fun checkDniFormat() : Boolean{
        return binding.txtDni.text.toString().length == 8 && binding.txtDni.text.toString().matches("^\\d{8}$".toRegex())
    }

    private fun checkPhoneFormat() : Boolean{
        return binding.txtTelf.text.toString().length == 9 && binding.txtTelf.text.toString().matches("^9\\d{8}$".toRegex())
    }

    /*-------Changing Colors Functions---------*/
    private fun changeColors(){
        binding.txtNombres.highlightColor = Color.parseColor("#EA5455")
        binding.txtApPaterno.highlightColor = Color.parseColor("#EA5455")
        binding.txtApMaterno.highlightColor = Color.parseColor("#EA5455")
        binding.txtDni.highlightColor = Color.parseColor("#EA5455")
        binding.txtEmail.highlightColor = Color.parseColor("#EA5455")
        binding.txtTelf.highlightColor = Color.parseColor("#EA5455")
    }

    private fun changeDniColors(){
        binding.txtDni.highlightColor = Color.parseColor("#EA5455")
    }

    private fun changeTelfColors(){
        binding.txtTelf.highlightColor = Color.parseColor("#EA5455")
    }

    /*-------Extra Functions---------*/
    private fun goToLogin(){
        val intent = Intent(applicationContext, LoginActivity::class.java)
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