package com.longdrink.androidapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.longdrink.androidapp.R
import com.longdrink.androidapp.api.ApiService
import com.longdrink.androidapp.databinding.FragmentMyCourseBinding
import com.longdrink.androidapp.model.Curso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    private lateinit var retrofit : Retrofit
    private var codCurso : Long = 0
    private val BASE_URL = "http://10.0.2.2:8080/api/v1/"
    private lateinit var curso : Curso

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyCourseBinding.inflate(inflater)
        retrofit = getRetrofit()
        codCurso = requireArguments().getLong("codCurso")
        getCourseInfo()
        return binding.root
    }



    private fun placeData(){
        binding.myCourseTeacherName.text = "${curso.profesor.nombre} ${curso.profesor.apellidoPaterno} ${curso.profesor.apellidoMaterno}"
        binding.myCourseScheduleHours.text = "${curso.turnos[0].horaInicio}"
    }

    private fun getCourseInfo(){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                if (codCurso != 0L){
                    val response : Response<Curso> =
                        retrofit.create(ApiService :: class.java).findCursoById(codCurso)

                    if (response.body() != null){
                        curso = response.body()!!
                        placeData()
                    }
                }
            }
            catch (ex : Exception){
                Log.e("ERROR EN LA OBTENCION DE CURSOS", ex.toString())
            }
        }
    }

    private fun getRetrofit(): Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
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