package com.longdrink.androidapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.longdrink.androidapp.api.ApiService
import com.longdrink.androidapp.databinding.ActivityInscriptionBinding
import com.longdrink.androidapp.model.Curso
import com.longdrink.androidapp.model.Inscripcion
import com.longdrink.androidapp.model.Turno
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.properties.Delegates

class InscriptionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityInscriptionBinding

    private val BASE_URL = "http://10.0.2.2:8080/api/v1/"
    private lateinit var retrofit : Retrofit

    private var cursos = mutableListOf<Curso>()
    private var nombresCursos = mutableListOf<String>()
    /** ARRAYS CON REFERENCIA A TURNOS, LOS CUALES SON FILTRADOS A PARTIR DEL CURSO SELECCIONADO EN EL SPINNER*/
    private var turnos = mutableListOf<Turno>()
    private var nombresTurnos = mutableListOf<String>()

    private var inscripcionesPorCurso = mutableListOf<Inscripcion>()

    private val fechasDeInicio = listOf<String>("01-10-2023", "01-11-2023", "01-12-2023", "01-01-2024")

    private var codAlum by Delegates.notNull<Long>()

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInscriptionBinding.inflate(layoutInflater)
        retrofit = getRetrofit()
        codAlum = intent.getLongExtra("codAlum", 0)
        Log.i("LLEGO CODALUM", "CODALUM -> $codAlum")
        setSupportActionBar(binding.inscriptionToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        fillingDateSpinner(fechasDeInicio)
        getCursos()

        binding.inscriptionSpinnerCourses.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                getTurnosByCodCurso(cursos[position].codCurso)
                fillingFrecuencyCourse(cursos[position])
            }

        }

        binding.inscriptionButton.setOnClickListener {
            val cursoSeleccionado = cursos[binding.inscriptionSpinnerCourses.selectedItemPosition]
            val datosInscripcion = getInscriptionData(cursoSeleccionado)
            val courseFull = verifingIfCourseFull(cursoSeleccionado)
            val checkDate = verifingDates(datosInscripcion)
            if (courseFull){
                showSnackbar("El curso cuenta con vacantes llenas.")
            }
            else if (checkDate){
                showSnackbar("Esta fecha de inicio ya ha vencido, seleccione otra")
            }
            else{
                runOnUiThread{
                    showingMessageAndSending("Inscripción", "Los datos de la inscripción son los siguientes: \n" +
                            "\n" +
                            "\t-Curso: ${binding.inscriptionSpinnerCourses.selectedItem}" +
                            "\n" +
                            "\t-Turno: ${binding.inscriptionSpinnerTime.selectedItem}"+
                            "\n" +
                            "\t-Fecha de inicio:${binding.inscriptionSpinnerDate.selectedItem}"+
                            "\n"+
                            "¿Desea continuar?",
                        datosInscripcion)
                }
            }

        }

        setContentView(binding.root)
    }

    private fun verifingDates(inscriptionData: InscripcionDetallada): Boolean {
        val formatterFechaInscripcion = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val fechaInicio = LocalDate.parse(inscriptionData.fechaInicio, formatterFechaInscripcion)
        val fechaInscripcion = LocalDate.parse(inscriptionData.fechaInscripcion, formatterFechaInscripcion)
        return fechaInicio <= fechaInscripcion
    }

    override fun onSupportNavigateUp(): Boolean {
        goToMain()
        return true
    }

    private fun getCursos(){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response : Response<List<Curso>>
                = retrofit.create(ApiService::class.java).listarCursos()

                if(response.isSuccessful){
                    if (cursos.isEmpty()){
                        response.body()?.forEach{ curso ->
                            cursos.add(curso)
                            nombresCursos.add(curso.descripcion)
                        }
                        runOnUiThread{
                            fillingCoursesSpinner(nombresCursos)
                        }
                    }
                }
                else{
                    Log.e("NO LLEGA", "NOOOOOOO")
                }

            }catch(ex : Exception){
                Log.e("INSCRIPTION_ACTIVITY_CURSOS", ex.toString())
            }
        }
    }

    private fun getTurnosByCodCurso(codCurso : Long){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response : Response<Curso>
                        = retrofit.create(ApiService::class.java).findCursoById(codCurso)

                if(response.isSuccessful){
                    turnos.clear()
                    nombresTurnos.clear()
                    response.body()?.turnos?.forEach{
                        turnos.add(it)
                        nombresTurnos.add(it.nombre)
                    }
                    runOnUiThread{
                        fillingTurnsSpinner(nombresTurnos)
                    }
                }
                else{
                    Log.e("NO LLEGA", "NOOOOOOO")
                }

            }catch(ex : Exception){
                Log.e("INSCRIPTION_ACTIVITY_CURSOS", ex.toString())
            }
        }
    }

    private fun sendInscription(inscripcion : InscripcionDetallada){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response : Response<Inscripcion>
                        = retrofit.create(ApiService::class.java).realizarInscripcion(inscripcion)

                if (response.isSuccessful){
                    runOnUiThread{
                        goToMain()
                    }
                }
            }catch (ex: Exception){
                Log.e("INSCRIPCION", "ERROR EN LA INSCRIPCION")
            }
        }
    }

    private fun getInscriptionsByCourse(codCurso: Long){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response : Response<List<Inscripcion>>
                        = retrofit.create(ApiService::class.java).listarInscripcionesByCursoId(codCurso)

                if(response.isSuccessful){
                    response.body()?.forEach {
                        if (it.estado){
                            inscripcionesPorCurso.add(it)
                        }
                    }
                }
                else{
                    Log.e("INSCRIPTION_BY_COURSE", "NOOOOOOO")
                }

            }catch(ex : Exception){
                Log.e("INSCRIPTION_BY_COURSE", ex.toString())
            }
        }
    }


    private fun verifingIfCourseFull( cursoSeleccionado : Curso) : Boolean{
        getInscriptionsByCourse(cursoSeleccionado.codCurso)
        if (inscripcionesPorCurso.size >= cursoSeleccionado.cantidadAlumnos){
            showSnackbar("El curso cuenta con las vacantes completas, por favor realice la inscripción en otro momento")
            return true
        }
        return false
    }

    private fun getInscriptionData(cursoSeleccionado: Curso): InscripcionDetallada {
        val formater = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formatterInscripcion = SimpleDateFormat("yyyy-MM-dd")
        val codAlumno = codAlum
        val codCurso = cursoSeleccionado.codCurso
        val fechaInicio = LocalDate.parse(binding.inscriptionSpinnerDate.selectedItem.toString(), formater)
        val fechaInscripcion = formatterInscripcion.format(Date())
        val fechaFinal =
            fechaInicio.plusWeeks(cursoSeleccionado.duracion.toLong())

        return InscripcionDetallada(
            codAlumno,
            codCurso,
            false,
            fechaInicio.toString(),
            fechaFinal.toString(),
            fechaInscripcion.toString()
        )
    }*/

    private fun fillingCoursesSpinner(nombresCursos : MutableList<String>){
        val adapterCursos = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nombresCursos)
        binding.inscriptionSpinnerCourses.adapter = adapterCursos
    }

    private fun fillingTurnsSpinner(nombresTurnos : MutableList<String>){
        binding.inscriptionSpinnerTime.adapter = null
        val adapterTurnos = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nombresTurnos)
        binding.inscriptionSpinnerTime.adapter = adapterTurnos
    }

    private fun fillingDateSpinner(fechasDeInicio : List<String>){
        val adapterFechas = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fechasDeInicio)
        binding.inscriptionSpinnerDate.adapter = adapterFechas
    }

    private fun fillingFrecuencyCourse(curso : Curso){
        binding.inscriptionTextFrecuency.text = curso.frecuencia.toString()
    }

    /*private fun showingMessageAndSending(titulo: String, mensaje : String, datosInscripcion : InscripcionDetallada){
        val mensajeEmergente = AlertDialog.Builder(this)

        mensajeEmergente.apply {
            setTitle(titulo)
            setMessage(mensaje)
            setPositiveButton("SI") { _: DialogInterface?, _: Int ->
                sendInscription(datosInscripcion)
            }
            setNegativeButton("NO"){ _: DialogInterface?, _: Int ->

            }
        }

        mensajeEmergente.create()
        mensajeEmergente.show()
    }*/

    private fun goToMain(){
        var intent = Intent(this@InscriptionActivity, MainActivity::class.java)
        intent.putExtra("codAlum", codAlum)
        startActivity(intent)
    }

    private fun showSnackbar(text : String){
        this.runOnUiThread { Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show() }
    }


    private fun getRetrofit() : Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}