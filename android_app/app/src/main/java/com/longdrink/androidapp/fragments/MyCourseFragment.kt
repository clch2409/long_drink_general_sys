package com.longdrink.androidapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.longdrink.androidapp.R
import com.longdrink.androidapp.adapters.TemasRecyclerViewAdapter
import com.longdrink.androidapp.api.ApiService
import com.longdrink.androidapp.databinding.FragmentMyCourseBinding
import com.longdrink.androidapp.model.Curso
import com.longdrink.androidapp.model.Tema
import com.longdrink.androidapp.retrofit.Api
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyCourseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyCourseFragment : Fragment() {
    private lateinit var binding : FragmentMyCourseBinding
    private var codCurso : Long = 0
    private var fechaFinal : String = ""
    private val listadoTemas : List<Tema> = emptyList()
    private lateinit var recyclerViewAdapter : TemasRecyclerViewAdapter
    private var apiService : ApiService? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyCourseBinding.inflate(inflater)
        apiService = Api.apiService
        codCurso = requireArguments().getLong("codCurso")
        fechaFinal = requireArguments().getString("fechaFinal").toString()
        getCourseInfo()
        return binding.root
    }



    private fun placeData(curso : Curso){
        val formato = SimpleDateFormat("yyyy-MM-dd")
        val fechaFinal = formato.parse(fechaFinal)
        formato.applyPattern("dd-MM-yyyy")
        val fechaFinalFormateada = formato.format(fechaFinal)


        binding.myCourseName.text = curso.nombre
        binding.myCourseTeacherName.text = "${curso.profesor.nombre} ${curso.profesor.apellidoPaterno} ${curso.profesor.apellidoMaterno}"
        binding.myCourseScheduleHours.text = "${curso.turnos[0].horaInicio} - ${curso.turnos[0].horaFin}"
        binding.myCourseFinishedDate.text = fechaFinalFormateada
        Picasso.get().load(curso.imagen).into(binding.myCourseImage)

        recyclerViewAdapter = TemasRecyclerViewAdapter(curso.temas)
        binding.recyclerMyClasess.setHasFixedSize(true)
        binding.recyclerMyClasess.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerMyClasess.adapter = recyclerViewAdapter

    }

    private fun getCourseInfo(){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                if (codCurso != 0L){
                    val response : Response<Curso> =
                        apiService!!.findCursoById(codCurso)

                    if (response.body() != null){
                        activity?.runOnUiThread{
                            Log.e("CURSO", response.body()!!.toString())
                            placeData(response.body()!!)
                        }
                    }
                }
            }
            catch (ex : Exception){
                Log.e("ERROR EN LA OBTENCION DE CURSOS", ex.toString())
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
         * @return A new instance of fragment MyCourseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyCourseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}