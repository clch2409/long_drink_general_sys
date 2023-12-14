package com.longdrink.androidapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.longdrink.androidapp.databinding.ActivityRecoveryBinding
import com.longdrink.androidapp.retrofit.Api
import com.longdrink.androidapp.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
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
                val response : Response<ResponseBody> =
                    Api.apiService.recuperarCredenciales(email)

                runOnUiThread{
                    val dialog : AlertDialog.Builder?

                    dialog = Utils.showDialog("Correo enviado", "Las credenciales han sido enviadas a su correo. Será redirigido a la pantalla de Inicio de Sesión", this@RecoveryActivity)
                    dialog.apply {
                        setNeutralButton("OK") { _: DialogInterface, _: Int ->
                            goToLogin()
                        }
                    }
                    dialog.create()
                    dialog.show()
                }
            }
            catch(ex : Exception){
                Log.e("RECOVERY ACTIVITY", ex.toString())
            }
        }
    }

    private fun checkEmpty() : Boolean{
        return binding.recoveryEmail.text.isEmpty()
    }

    private fun goToLogin(){
        finish()
        val intent = Intent(this@RecoveryActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}