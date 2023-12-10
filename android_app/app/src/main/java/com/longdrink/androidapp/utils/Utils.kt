package com.longdrink.androidapp.utils

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

object Utils {

    fun showSnackbar(mensaje : String, view : View){
        Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG)
    }

    /*fun goToSomewhere(contexto : Context, destino : Class<Class>){
        ContextCompat.startActivity(contexto, Intent(contexto, destino), null)
    }*/

}