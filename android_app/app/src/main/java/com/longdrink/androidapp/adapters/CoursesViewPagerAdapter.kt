package com.longdrink.androidapp.adapters

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.longdrink.androidapp.fragments.CoursesFragment
import com.longdrink.androidapp.fragments.MyAccountFragment
import com.longdrink.androidapp.fragments.MyCourseFragment
import com.longdrink.androidapp.fragments.NoActiveCourseFragment
import com.longdrink.androidapp.fragments.NoCourseFragment
import com.longdrink.androidapp.model.Alumno
import com.longdrink.androidapp.model.Inscripcion

class CoursesViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private var inscription : Inscripcion,
    private var listadoInscripcion : List<Inscripcion>,
    private var email : String,
    private var usuario : String,
    private var nombreCompleto : String
) : FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        lateinit var fragment: Fragment
        lateinit var bundle: Bundle

         if (position == 0) {
            bundle = bundleOf(Pair("inscripcion", inscription))
            if (inscription.fechaInscripcion == ""){
                fragment = NoActiveCourseFragment()
            }else{
                fragment = MyCourseFragment()
                fragment.arguments = bundle
            }
            fragment
        }else if(position == 1){
            bundle = bundleOf(Pair("inscripcionesTerminadas", listadoInscripcion), Pair("inscripcionActiva", inscription))
            if (listadoInscripcion.isEmpty()){
                fragment = NoCourseFragment()
            }
            else{
                fragment = CoursesFragment()
                fragment.arguments = bundle
            }
            fragment
        } else{
            bundle = bundleOf(Pair("email", email), Pair("usuario", usuario), Pair("nombreCompleto", nombreCompleto), Pair("codAlumno", inscription.codAlum), Pair("inscripcion", inscription))
            fragment = MyAccountFragment()
            fragment.arguments = bundle
        }

        return fragment
    }
}