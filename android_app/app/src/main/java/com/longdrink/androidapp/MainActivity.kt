package com.longdrink.androidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.longdrink.androidapp.adapters.CoursesViewPagerAdapter
import com.longdrink.androidapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var adapter : CoursesViewPagerAdapter
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        adapter = CoursesViewPagerAdapter(supportFragmentManager, lifecycle)

        initUi()

        setContentView(binding.root)
    }

    private fun initUi(){

        binding.tablayout.addTab(binding.tablayout.newTab().setText("Cursos"))
        binding.tablayout.addTab(binding.tablayout.newTab().setText("Mi Curso"))
        binding.tablayout.addTab(binding.tablayout.newTab().setText("Mi Cuenta"))

        binding.mainViewpager.adapter = adapter

        addingListeners()
    }

    private fun addingListeners(){
        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null){
                    binding.mainViewpager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        binding.mainViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tablayout.selectTab(binding.tablayout.getTabAt(position))
            }
        })
    }
}