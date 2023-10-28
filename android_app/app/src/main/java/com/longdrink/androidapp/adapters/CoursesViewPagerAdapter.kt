package com.longdrink.androidapp.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.longdrink.androidapp.api.ApiService
import com.longdrink.androidapp.databinding.FragmentMyCourseBinding
import com.longdrink.androidapp.fragments.CoursesFragment
import com.longdrink.androidapp.fragments.MyAccountFragment
import com.longdrink.androidapp.fragments.MyCourseFragment
import com.longdrink.androidapp.fragments.NoCourseFragment
import com.longdrink.androidapp.model.Curso
import com.longdrink.androidapp.model.Inscripcion
import com.longdrink.androidapp.model.InscripcionPK
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
    private var studentId : Long,
    private var hasInscription : Boolean,
    private var inscription : Inscripcion = Inscripcion()
) : FragmentStateAdapter(fragmentManager, lifecycle){

    private val BASE_URL = "http://10.0.2.2:8080/api/v1/"
    private val retrofit = getRetrofit()


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {

        return if (position == 0) {
            CoursesFragment()
        } else if (position == 1) {
            if (hasInscription) {
                var bundle = bundleOf(Pair("codCurso" , inscription.inscripcionPK.codCurso))
                var fragment = MyCourseFragment()
                fragment.arguments = bundle
                fragment
            }
            else{
                NoCourseFragment()
            }

        } else{
            MyAccountFragment()
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