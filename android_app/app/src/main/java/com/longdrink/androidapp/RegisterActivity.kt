package com.longdrink.androidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.longdrink.androidapp.databinding.ActivityRegisterBinding
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



        setContentView(R.layout.activity_register)
    }

        
    private fun getRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}