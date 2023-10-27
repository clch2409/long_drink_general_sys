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
class CoursesFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentCoursesBinding
    private lateinit var adapter : CourseRecyclerViewAdapter
    private lateinit var retrofit: Retrofit
    private val BASE_URL = "http://10.0.2.2:8080/api/v1/"

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
        retrofit = getRetrofi()
        getCursos()
        return binding.root
    }

    private fun initUI(listadoCursos : List<Curso>){
        /*TODO --> BUSCAR LA MANERA DE AGREGAR UN SEPARADOR PERSONALIZADO*/

        adapter = CourseRecyclerViewAdapter(listadoCursos)
        binding.recyclerCourses.setHasFixedSize(true)
        binding.recyclerCourses.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerCourses.adapter = adapter


    }

    private fun getCursos(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response : Response<List<Curso>> =
                    retrofit.create(ApiService::class.java).listarCursos()
                if (response.isSuccessful){
                    val myResponse : List<Curso>? = response.body()
                    if (myResponse != null){
                        initUI(myResponse)
                        Log.i("LLEGÓ", myResponse.toString())
                    }
                }
            }catch (ex : Exception){
                Log.i("ERROR", ex.toString())
            }
        }
    }

    private fun getRetrofi() : Retrofit{
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