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
import com.longdrink.androidapp.fragments.WaitingInscriptionFragment
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
    private var hasInscription : Boolean,
    private var inscription : Inscripcion = Inscripcion(),
    private var codAlum : Long
) : FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {

        return if (position == 0) {
            var bundle = bundleOf(Pair("codAlum", codAlum))
            var fragment = CoursesFragment()
            fragment.arguments = bundle
            fragment
        } else if (position == 1) {
            if (hasInscription) {
                if(!inscription.estado){
                    WaitingInscriptionFragment()
                }
                else{
                    var bundle = bundleOf(Pair("codCurso" , inscription.inscripcionPK.codCurso))
                    var fragment = MyCourseFragment()
                    fragment.arguments = bundle
                    fragment
                }
            }
            else{
                NoCourseFragment()
            }

        } else{
            MyAccountFragment()
        }
    }
}