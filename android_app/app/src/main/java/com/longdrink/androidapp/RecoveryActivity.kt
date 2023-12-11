package com.longdrink.androidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.longdrink.androidapp.databinding.ActivityRecoveryBinding
import com.longdrink.androidapp.model.Mensaje
import com.longdrink.androidapp.retrofit.Api
import com.longdrink.androidapp.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.Util
import retrofit2.Response

class RecoveryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecoveryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecoveryBinding.inflate(layoutInflater)

        binding.recoveryButton.setOnClickListener {
            if (checkEmpty()){
                Utils.showSnackbar("Ingrese el correo solicitado", binding.root)
            }else{
                recuperarCuenta(binding.recoveryEmail.text.toString())
            }
        }

        setContentView(binding.root)
    }

    private fun recuperarCuenta(email : String){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response : Response<Mensaje?> =
                    Api.apiService.recuperarCredenciales(email)

                Log.i("RECOVERY", response.message().toString())
            }
            catch(ex : Exception){
                Log.e("RECOVERY ACTIVITY", ex.toString())
            }
        }
    }

    private fun checkEmpty() : Boolean{
        return binding.recoveryEmail.text.isEmpty()
    }
}