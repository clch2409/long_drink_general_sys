package com.longdrink.androidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.longdrink.androidapp.api.ApiService
import com.longdrink.androidapp.databinding.ActivityRecoveryBinding
import com.longdrink.androidapp.model.CambiarCredenciales
import com.longdrink.androidapp.model.Mensaje
import com.longdrink.androidapp.retrofit.Api
import com.longdrink.androidapp.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class RecoveryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecoveryBinding
    private lateinit var apiService : ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecoveryBinding.inflate(layoutInflater)
        apiService = Api.apiService
        setSupportActionBar(binding.recoveryToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)


        binding.recoveryButton.setOnClickListener {
            if (verificarVacio()){
                mostrarSnackbar("Llene todos los campos requeridos, por favor.")
            }
            else{
                enviarDatos(obtenerDatos())
            }
        }


        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun obtenerDatos(): CambiarCredenciales {
        val viejoEmail = binding.oldEmail.text.toString()
        val nuevoEmail = binding.newEmail.text.toString()
        val viejaContra = binding.oldPassword.text.toString()
        val nuevaContra = binding.newPassword.text.toString()

        return CambiarCredenciales(viejaContra, nuevaContra, viejoEmail, nuevoEmail)
    }

    private fun enviarDatos(cambiarCredenciales: CambiarCredenciales){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response : Response<Mensaje> = apiService.cambiarCredenciales(cambiarCredenciales)

                if (!response.isSuccessful){
                    mostrarSnackbar("Verifique los datos que ha ingresado, pueden no ser correctos")
                }
                else{
                    mostrarSnackbar("Credenciales actualizadas!")
                }
            }catch (ex : Exception){
                Log.e("RECOVERY ACTIVITY", ex.toString())
            }
        }
    }

    private fun verificarVacio() : Boolean{
        val oldEmail = binding.oldEmail.text
        val newEmail = binding.newEmail.text
        val newPass = binding.newPassword.text
        val oldPass = binding.oldPassword.text

        return oldPass.isEmpty() || newPass.isEmpty() || newEmail.isEmpty() || oldEmail.isEmpty()
    }

    private fun mostrarSnackbar(mensaje : String){
        Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_LONG).show();
    }

    private fun goToMain(){
        finish()
        val intent = Intent(this@RecoveryActivity, MainActivity::class.java)
        startActivity(intent)
    }
}