package com.longdrink.androidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.longdrink.androidapp.databinding.ActivityRegisterPasswordsBinding
import com.longdrink.androidapp.model.RegisterSendData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterPasswordsActivity : AppCompatActivity() {
    val BASE_URL = "http://10.0.2.2:8080/api/v1/"
    lateinit var retrofit: Retrofit
    lateinit var binding : ActivityRegisterPasswordsBinding
    lateinit var recivedData : RegisterSendData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterPasswordsBinding.inflate(layoutInflater)
        retrofit = getRetrofit()

        setContentView(R.layout.activity_register_passwords)
    }

    private fun checkEmpty(): Boolean{
        return binding.txtPass.text.isEmpty()
                || binding.txtPassRepeat.text.isEmpty()
    }

    private fun getRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}