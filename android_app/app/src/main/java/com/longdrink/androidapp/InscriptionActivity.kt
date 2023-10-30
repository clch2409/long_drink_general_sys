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
import com.longdrink.androidapp.api.ApiService
import com.longdrink.androidapp.databinding.ActivityInscriptionBinding
import com.longdrink.androidapp.model.Curso
import com.longdrink.androidapp.model.Inscripcion
import com.longdrink.androidapp.model.InscripcionDetallada
import com.longdrink.androidapp.model.InscripcionPK
import com.longdrink.androidapp.model.Turno
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    private var codAlum by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInscriptionBinding.inflate(layoutInflater)
        retrofit = getRetrofit()
        codAlum = 7
        Log.i("LLEGO CODALUM", "CODALUM -> $codAlum")
        setSupportActionBar(binding.inscriptionToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)

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
            showingMessageAndSending("Inscripción", "Los datos de la inscripción son los siguientes: \n" +
                    "\n" +
                    "\t-Curso: ${binding.inscriptionSpinnerCourses.selectedItem}" +
                    "\n" +
                    "\t-Turno: ${binding.inscriptionSpinnerTime.selectedItem}"+
                    "\n" +
                    "¿Desea continuar?")
        }

        setContentView(binding.root)
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
                    inscripcionesPorCurso = response.body() as MutableList<Inscripcion>
                }
                else{
                    Log.e("INSCRIPTION_BY_COURSE", "NOOOOOOO")
                }

            }catch(ex : Exception){
                Log.e("INSCRIPTION_BY_COURSE", ex.toString())
            }
        }
    }

    /*private fun verifingIfCourseFull(inscripcionObjetos: InscripcionObjetos){
        val fechaHoy = Date()
        val otraFechaHoy = Date()
        Log.i("FECHA", "${fechaHoy > otraFechaHoy}")
        if (inscripcionObjetos.curso.cantidadAlumnos <= inscripcionesPorCurso.size.toByte()){

        }
    }*/

    private fun getInscriptionData(): InscripcionDetallada {
        val cursoSeleccionado = cursos[binding.inscriptionSpinnerCourses.selectedItemPosition]
        val formater = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val codAlumno = codAlum
        val codCurso = cursoSeleccionado.codCurso
        val fechaInicio = "2023-11-01"
        val fechaInscripcion = LocalDate.parse("2023-10-29", formater)
        val fechaFinal =
            LocalDate.parse(fechaInicio, formater).plusWeeks(cursoSeleccionado.duracion.toLong())

        return InscripcionDetallada(
            codAlumno,
            codCurso,
            false,
            fechaInicio,
            fechaFinal.toString(),
            fechaInscripcion.toString()
        )
    }

    private fun fillingCoursesSpinner(nombresCursos : MutableList<String>){
        val adapterCursos = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nombresCursos)
        binding.inscriptionSpinnerCourses.adapter = adapterCursos
    }

    private fun fillingTurnsSpinner(nombresTurnos : MutableList<String>){
        binding.inscriptionSpinnerTime.adapter = null
        val adapterTurnos = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nombresTurnos)
        binding.inscriptionSpinnerTime.adapter = adapterTurnos
    }

    private fun fillingFrecuencyCourse(curso : Curso){
        binding.inscriptionTextFrecuency.text = curso.frecuencia.toString()
    }

    private fun showingMessageAndSending(titulo: String, mensaje : String){
        val mensajeEmergente = AlertDialog.Builder(this)

        mensajeEmergente.apply {
            setTitle(titulo)
            setMessage(mensaje)
            setPositiveButton("SI") { _: DialogInterface?, _: Int ->
                sendInscription(getInscriptionData())
            }
            setNegativeButton("NO"){ _: DialogInterface?, _: Int ->

            }
        }

        mensajeEmergente.create()
        mensajeEmergente.show()
    }

    private fun goToMain(){
        var intent = Intent(this@InscriptionActivity, MainActivity::class.java)
        intent.putExtra("codAlum", codAlum)
        startActivity(intent)
    }


    private fun getRetrofit() : Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}