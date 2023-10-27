package com.longdrink.androidapp.adapters

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.longdrink.androidapp.api.ApiService
import com.longdrink.androidapp.fragments.CoursesFragment
import com.longdrink.androidapp.fragments.MyAccountFragment
import com.longdrink.androidapp.fragments.MyCourseFragment
import com.longdrink.androidapp.fragments.NoCourseFragment
import com.longdrink.androidapp.model.Curso
import com.longdrink.androidapp.model.Inscripcion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

class CoursesViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private var studentId : Long
) : FragmentStateAdapter(fragmentManager, lifecycle){

    private val BASE_URL = "http://10.0.2.2:8080/api/v1/"
    private var hasInscription: Boolean = false
    private val retrofit = getRetrofit()
    private lateinit var inscripcion : Inscripcion
    private lateinit var bundle : Bundle

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        getInscription()
        return if (position == 0){
            CoursesFragment()
        } else if (position == 1){
            if (hasInscription){
                bundle.putLong("codCurso", inscripcion.inscripcionPK.codCurso)
                var mCourseFrag : MyCourseFragment = MyCourseFragment()
                mCourseFrag.arguments = bundle
                mCourseFrag
            }
            else{
                NoCourseFragment()
            }
        } else{
            MyAccountFragment()
        }
    }

    /*private fun getCourseInfo() {
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val response : Response<Curso> =
                    retrofit.create(ApiService :: class.java).findCursoById(inscripcion.inscripcionPK.codCurso)

                if (response.body() != null){
                    curso = response.body()!!
                }
            }
            catch (ex : Exception){
                Log.e("ERROR EN LA OBTENCION DE CURSOS", ex.toString())
            }
        }
    }*/

    private fun getInscription(){
        CoroutineScope(Dispatchers.IO).launch{
            try{
                val response : Response<List<Inscripcion>> =
                    retrofit.create(ApiService::class.java).listarInscripcionesByAlumnoId(studentId)

                hasInscription = response.isSuccessful
                if (hasInscription){
                    response.body()?.forEach{
                        if (it.estado) inscripcion = it
                    }
                }
            }catch(ex : Exception){
                Log.e("ERROR EN OBTENER INSCRIPCION", ex.toString())
            }
        }
    }

    private fun getRetrofit() : Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}