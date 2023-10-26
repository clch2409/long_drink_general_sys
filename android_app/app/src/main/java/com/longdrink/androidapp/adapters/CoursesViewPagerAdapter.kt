package com.longdrink.androidapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.longdrink.androidapp.fragments.CoursesFragment
import com.longdrink.androidapp.fragments.MyAccountFragment
import com.longdrink.androidapp.fragments.MyCourseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoursesViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    studentId : Int
) : FragmentStateAdapter(fragmentManager, lifecycle){

    private val BASE_URL = "http://10.0.2.2:8080/api/v1/"

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0){
            CoursesFragment()
        } else if (position == 1){
            MyCourseFragment()
        } else{
            MyAccountFragment()
        }
    }

    private fun getInscription(){
        val retrofit = getRetrofit()
        CoroutineScope(Dispatchers.IO){

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