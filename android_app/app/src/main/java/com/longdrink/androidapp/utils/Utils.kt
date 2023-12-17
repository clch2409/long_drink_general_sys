package com.longdrink.androidapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.longdrink.androidapp.R
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone

object Utils {

    @SuppressLint("ResourceAsColor")
    fun showSnackbar(mensaje : String, view : View){
        Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG).setBackgroundTint(R.color.light_red_error).show()

    }

    fun obtenerFechaHoy() : String{
        return ZonedDateTime.now(ZoneId.of("America/Lima"))
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

    fun obtenerCalendarioActual() : GregorianCalendar{
        val timezone = TimeZone.getDefault()
        val calendario = GregorianCalendar(timezone)
        val fechaHoy = LocalDateTime.now()
        calendario.set(fechaHoy.year, fechaHoy.monthValue - 1, fechaHoy.dayOfMonth)
        return calendario
    }

    fun obtenerAñoActual(calendario : GregorianCalendar) : Int{
        return calendario.get(Calendar.YEAR)
    }

    fun obtenerDiaSemana(calendario : GregorianCalendar): Int {
        return calendario.get(Calendar.DAY_OF_WEEK)
    }

    fun obtenerDiaAño(calendario : GregorianCalendar): Int{
        return calendario.get(Calendar.DAY_OF_YEAR)
    }

    fun obtenerHoraActual(): String {
        return ZonedDateTime.now(ZoneId.of("America/Lima"))
            .format(DateTimeFormatter.ofPattern("hh:mm"))
    }

    fun devolverFecha(fechaString : String) : Date{
        val deStringAFecha = SimpleDateFormat("yyyy-MM-dd")

        var fecha = deStringAFecha.parse(fechaString)

        return fecha
    }

    fun devolverFechaString(fechaString : String): String{
        val deStringAFecha = SimpleDateFormat("yyyy-MM-dd")
        val deFechaAString = SimpleDateFormat("dd-MM-yyyy")

        var fecha = deStringAFecha.parse(fechaString)
        var nuevaFecha = deFechaAString.format(fecha)

        return nuevaFecha
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