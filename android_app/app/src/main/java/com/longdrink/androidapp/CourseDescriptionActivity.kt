package com.longdrink.androidapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.longdrink.androidapp.databinding.ActivityCourseDescriptionBinding
import com.longdrink.androidapp.model.Curso
import com.longdrink.androidapp.model.Inscripcion
import com.longdrink.androidapp.model.ListItemCursoTerminado
import com.longdrink.androidapp.retrofit.Api
import com.longdrink.androidapp.utils.Utils
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import kotlin.properties.Delegates

class CourseDescriptionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCourseDescriptionBinding
    private lateinit var inscripcion : Inscripcion
    private var codAlum by Delegates.notNull<Long>()
    private var permitirCertificado = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCourseDescriptionBinding.inflate(layoutInflater)
        setSupportActionBar(binding.courseDescriptionToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        codAlum = intent.getLongExtra("codAlum", 0)
        inscripcion = intent.getSerializableExtra("inscripcion",) as Inscripcion
        /** Verificar si funciona el action bar del boton atrás*/
        initUI()

        setContentView(binding.root)
    }

    override fun onBackPressed() {

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun initUI() {

        binding.courseDescriptionName.text = inscripcion.seccion.curso.nombre
        binding.courseDescriptionStart.text = "Inicio del Curso: ${mostrarFecha(inscripcion.seccion.fechaInicio)}"
        binding.courseDescriptionEnd.text = "Finalización del curso: ${mostrarFecha(inscripcion.seccion.fechaFinal)}"
        binding.courseDescriptionState.text = "Estado: ${validarTerminadoRetirado(inscripcion)}"
        Picasso.get().load(inscripcion.seccion.curso.imagen).into(binding.courseDescriptionImage)
        binding.courseDescriptionCertificate.setOnClickListener {
            if (permitirCertificado){
                descargarCertificado(codAlum, inscripcion.seccion.curso.codCurso)
            }
            else{
                Utils.showSnackbar("Usted no ha terminado el curso, no puede descargar el certificado 😢", binding.root)
            }
        }
    }

    /*override fun onBackPressed() {
        finish()
        sendToMain()
    }*/

    /*private fun sendToInscription(){
        val intent = Intent(applicationContext, InscriptionActivity::class.java)
        intent.putExtra("codAlum", codAlum)
        startActivity(intent)
    }*/

    private fun goToMain(){
        var intent = Intent(this@CourseDescriptionActivity, MainActivity::class.java)
        intent.putExtra("codAlum", codAlum)
        startActivity(intent)
    }

    /*private fun showSnackbar(text : String){
        this.runOnUiThread { Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show() }
    }*/

    private fun mostrarFecha(fecha : String) : String{
        var deStringAFecha = SimpleDateFormat("yyyy-MM-dd")
        var deFechaAString = SimpleDateFormat("dd-MM-yyyy")

        var fechaDate : Date = deStringAFecha.parse(fecha)
        var fechaString : String = deFechaAString.format(fechaDate)

        return fechaString
    }

    private fun validarTerminadoRetirado(inscripcion: Inscripcion) : String{
        return if (!inscripcion.estado && !inscripcion.seccion.estado){ //(inscripcion.fechaTerminado == inscripcion.seccion.fechaFinal)
            permitirCertificado = true
            "Terminado"
        } else{
            "Retirado"
        }
    }

    private fun validarDescargarCertificado() : Boolean{
        if (binding.courseDescriptionState.text == "Terminado"){
            return true
        }
        return false
    }

    private fun descargarCertificado(codAlumno : Long, codCurso : Long){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Api.BASE_URL + "reporte/certificado/pdf?codAlum=$codAlumno&codCurso=$codCurso"))
        ContextCompat.startActivity(this, intent, null)
    }
}