package com.longdrink.androidapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.longdrink.androidapp.R
import com.longdrink.androidapp.adapters.CourseRecyclerViewAdapter
import com.longdrink.androidapp.api.ApiService
import com.longdrink.androidapp.databinding.FragmentCoursesBinding
import com.longdrink.androidapp.model.Curso
import com.longdrink.androidapp.model.Inscripcion
import com.longdrink.androidapp.model.ListItemCursoTerminado
import com.longdrink.androidapp.retrofit.Api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class CoursesFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var apiService : ApiService? = null
    private lateinit var binding : FragmentCoursesBinding
    private lateinit var adapter : CourseRecyclerViewAdapter
    private var codAlum by Delegates.notNull<Long>()
    private val cursosTerminadosFiltrados = mutableListOf<ListItemCursoTerminado>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoursesBinding.inflate(inflater)
        apiService = apiService
        codAlum = requireArguments().getLong("codAlum")
        getCursos()
        return binding.root
    }

    private fun initUI(cursosTerminadosFiltrados : List<ListItemCursoTerminado>){
        /*TODO --> BUSCAR LA MANERA DE AGREGAR UN SEPARADOR PERSONALIZADO*/

        adapter = CourseRecyclerViewAdapter(cursosTerminadosFiltrados, codAlum)
        binding.recyclerCourses.setHasFixedSize(true)
        binding.recyclerCourses.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerCourses.adapter = adapter


    }

    private fun getCursos(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response : Response<List<ListItemCursoTerminado>> =
                    apiService!!.listarCursos()
                if (response.isSuccessful){
                    val myResponse : List<ListItemCursoTerminado>? = response.body()
                    if (myResponse != null){
                        getInscripcion(myResponse)
                    }
                }
            }catch (ex : Exception){
                Log.i("ERROR", ex.toString())
            }
        }
    }

    private fun getInscripcion(cursos : List<ListItemCursoTerminado>){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response : Response<List<Inscripcion>> =
                    apiService!!.listarInscripcionesByAlumnoId(codAlum)
                if (response.isSuccessful){
                    val myResponse : List<Inscripcion>? = response.body()
                    if (myResponse != null){
                        activity?.runOnUiThread{
                            for (curso : ListItemCursoTerminado in cursos){
                                for (inscripcion : Inscripcion in myResponse){
                                    if (curso.codCurso == inscripcion.curso && !inscripcion.estado){
                                        curso.fechaTerminadoInscripcion = inscripcion.fechaTerminado
                                        curso.fechaInicioInscripcion = inscripcion.fechaInscripcion
                                        curso.fechaFinalInscripcion = inscripcion.fechaFinal
                                        curso.fechaInscripcion = inscripcion.fechaInscripcion
                                        curso.estadoInscripcion = inscripcion.estado
                                        cursosTerminadosFiltrados.add(curso)
                                    }
                                }
                            }
                            initUI(cursosTerminadosFiltrados)
                        }
                    }
                }
            }catch (ex : Exception){
                Log.i("ERROR", ex.toString())
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CoursesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CoursesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}