package com.longdrink.androidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.longdrink.androidapp.databinding.ActivityCourseDescriptionBinding
import com.longdrink.androidapp.model.Curso
import com.longdrink.androidapp.model.ListItemCursoTerminado
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import kotlin.properties.Delegates

class CourseDescriptionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCourseDescriptionBinding
    private lateinit var courseData : ListItemCursoTerminado
    private var codAlum by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCourseDescriptionBinding.inflate(layoutInflater)
        setSupportActionBar(binding.courseDescriptionToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        codAlum = intent.getLongExtra("codAlum", 0)
        /** Verificar si funciona el action bar del boton atrás*/
        initUI()

        setContentView(binding.root)
    }

    override fun onBackPressed() {

    }

    override fun onSupportNavigateUp(): Boolean {
        goToMain()
        return true
    }

    private fun initUI() {
        courseData.nombre = intent.getStringExtra("courseName").toString()
        courseData.fechaInicioInscripcion = intent.getStringExtra("fechaInicio")
        courseData.fechaTerminadoInscripcion = intent.getStringExtra("fechaTerminado").toString()

        binding.courseDescriptionName.text = courseData.nombre
        binding.courseDescriptionStart.text = "Inicio del Curso: ${mostrarFecha(courseData.fechaInicioInscripcion!!)}"
        binding.courseDescriptionEnd.text = "Finalización del curso: ${mostrarFecha(courseData.fechaTerminadoInscripcion!!)}"
        binding.courseDescriptionState.text = "Estado: ${validarTerminadoRetirado(courseData)}"
        Picasso.get().load(courseData.imagen).into(binding.courseDescriptionImage)
        binding.courseDescriptionCertificate.setOnClickListener { showSnackbar("No Funcionando 🤡") }
    }

    /*override fun onBackPressed() {
        finish()
        sendToMain()
    }*/

    private fun sendToInscription(){
        val intent = Intent(applicationContext, InscriptionActivity::class.java)
        intent.putExtra("codAlum", codAlum)
        startActivity(intent)
    }

    private fun goToMain(){
        var intent = Intent(this@CourseDescriptionActivity, MainActivity::class.java)
        intent.putExtra("codAlum", codAlum)
        startActivity(intent)
    }

    private fun showSnackbar(text : String){
        this.runOnUiThread { Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show() }
    }

    private fun mostrarFecha(fecha : String) : String{
        var deStringAFecha = SimpleDateFormat("yyyy-MM-dd")
        var deFechaAString = SimpleDateFormat("dd-MM-yyyy")

        var fechaDate : Date = deStringAFecha.parse(fecha)
        var fechaString : String = deFechaAString.format(fechaDate)

        return fechaString
    }

    private fun validarTerminadoRetirado(cursoTerminado : ListItemCursoTerminado) : String{
        if (!cursoTerminado.estadoInscripcion!! && cursoTerminado.fechaTerminadoInscripcion == cursoTerminado.fechaInscripcion){
            return "Retirado"
        }
        else{
            return "Terminado"
        }
    }
}