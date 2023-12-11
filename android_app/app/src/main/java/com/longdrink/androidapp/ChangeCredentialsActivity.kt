package com.longdrink.androidapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.longdrink.androidapp.api.ApiService
import com.longdrink.androidapp.databinding.ActivityChangeCredentialsBinding
import com.longdrink.androidapp.databinding.ActivityRecoveryBinding
import com.longdrink.androidapp.model.CambiarCredenciales
import com.longdrink.androidapp.model.Mensaje
import com.longdrink.androidapp.retrofit.Api
import com.longdrink.androidapp.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ChangeCredentialsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChangeCredentialsBinding
    private lateinit var apiService : ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangeCredentialsBinding.inflate(layoutInflater)
        apiService = Api.apiService
        setSupportActionBar(binding.recoveryToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)


        binding.recoveryButton.setOnClickListener {
            if (verificarVacio()){
                Utils.showSnackbar("Llene todos los campos requeridos, por favor.", binding.root)
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

                runOnUiThread{
                    var alertDialog : AlertDialog.Builder? = null

                    if (!response.isSuccessful){
                        alertDialog = Utils.showDialog("Verificar Datos", "Verifique que sus datos hayan sido ingresados correctamente, por favor", this@ChangeCredentialsActivity)
                        alertDialog.setNeutralButton("OK") { _:DialogInterface?, _: Int ->
                        }
                    } else{
                        alertDialog = Utils.showDialog("Datos Actualizados","!Felicidades, datos actualizados correctamente 🥳! ¿Desea regresar a la pantalla de Login?", this@ChangeCredentialsActivity)
                        alertDialog.setPositiveButton("SI") { _: DialogInterface?, _: Int ->
                            goToLogin()
                        }
                        alertDialog.setNegativeButton("NO") { _: DialogInterface, _: Int ->
                            clearBoxes()
                        }
                    }


                        alertDialog.create()
                        alertDialog.show()
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

    private fun clearBoxes(){
        binding.oldEmail.text.clear()
        binding.newEmail.text.clear()
        binding.oldPassword.text.clear()
        binding.newPassword.text.clear()
    }

    /*private fun mostrarSnackbar(mensaje : String){
        Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_LONG).show();
    }*/

    private fun goToLogin(){
        finish()
        val intent = Intent(this@ChangeCredentialsActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun goToMain(){
        finish()
    }
}