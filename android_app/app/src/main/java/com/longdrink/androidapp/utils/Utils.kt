package com.longdrink.androidapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.longdrink.androidapp.R

object Utils {

    @SuppressLint("ResourceAsColor")
    fun showSnackbar(mensaje : String, view : View){
        Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG).setBackgroundTint(R.color.light_red_error).show()

    }

    /*fun goToSomewhere(contexto : Context, destino : Class<Class>){
        ContextCompat.startActivity(contexto, Intent(contexto, destino), null)
    }*/


    fun showDialog(titulo: String, mensaje : String, contexto : Context): AlertDialog.Builder {
        val mensajeEmergente = AlertDialog.Builder(contexto)

        mensajeEmergente.apply {
            setTitle(titulo)
            setMessage(mensaje)
        }

        return mensajeEmergente
    }
}