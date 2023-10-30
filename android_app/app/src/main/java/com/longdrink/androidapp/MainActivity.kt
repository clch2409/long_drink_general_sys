package com.longdrink.androidapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.longdrink.androidapp.adapters.CoursesViewPagerAdapter
import com.longdrink.androidapp.api.ApiService
import com.longdrink.androidapp.databinding.ActivityMainBinding
import com.longdrink.androidapp.model.Inscripcion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var adapter : CoursesViewPagerAdapter
    private lateinit var binding : ActivityMainBinding
    private val BASE_URL = "http://10.0.2.2:8080/api/v1/"
    private lateinit var retrofit : Retrofit
    private var inscripcion = Inscripcion()
    private var hasInscription = false
    private var codAlum by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        codAlum = intent.getLongExtra("codAlum", 0)
        binding = ActivityMainBinding.inflate(layoutInflater)
        retrofit = getRetrofit()
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setLogo(R.mipmap.ic_logo_foreground)
        initUi()

        setContentView(binding.root)
    }

    override fun onBackPressed() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_logout) {
            finish()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        return true
    }

    private fun initUi(){
        getInscription()

        binding.tablayout.addTab(binding.tablayout.newTab().setText("Cursos"))
        binding.tablayout.addTab(binding.tablayout.newTab().setText("Mi Curso"))
        /*binding.tablayout.addTab(binding.tablayout.newTab().setText("Mi Cuenta"))*/

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

    private fun getInscription(){
        CoroutineScope(Dispatchers.IO).launch{
            try{
                val response : Response<List<Inscripcion>> =
                    retrofit.create(ApiService::class.java).listarInscripcionesByAlumnoId(codAlum)

                if (response.isSuccessful){
                    Log.i("INSCRIPCION", response.body().toString())
                    response.body()?.forEach{
                        if (it.estado || (it.fechaInicio != "" && it.fechaTerminado == null)) inscripcion = it
                    }
                    runOnUiThread{
                        hasInscription = inscripcion.fechaInicio != ""
                        adapter = CoursesViewPagerAdapter(supportFragmentManager, lifecycle, hasInscription ,inscripcion, codAlum )
                        binding.mainViewpager.adapter = adapter

                    }
                }

            }catch(ex : Exception){
                Log.e("ERROR EN OBTENER INSCRIPCION", ex.toString())
            }
        }
    }

    private fun getRetrofit() : Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}