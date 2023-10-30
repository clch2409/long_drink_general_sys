package com.longdrink.androidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.longdrink.androidapp.databinding.ActivityCourseDescriptionBinding
import com.longdrink.androidapp.model.Curso
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates

class CourseDescriptionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCourseDescriptionBinding
    private lateinit var courseData : Curso
    private var codAlum by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCourseDescriptionBinding.inflate(layoutInflater)
        setSupportActionBar(binding.courseDescriptionToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        codAlum = intent.getLongExtra("codAlum", 0)
        /** Verificar si funciona el action bar del boton atrás*/
        initUI()

        setContentView(binding.root)
    }

    override fun onBackPressed() {

    }

    override fun onSupportNavigateUp(): Boolean {
        goToMain()
        return true
    }

    private fun initUI() {
        courseData = Curso()
        courseData.descripcion = intent.getStringExtra("courseName").toString()
        courseData.mensualidad = intent.getDoubleExtra("price", 0.0)
        courseData.imagen = intent.getStringExtra("image").toString()

        binding.courseDescriptionName.text = courseData.descripcion
        binding.courseDescriptionPrice.text = courseData.mensualidad.toString()
        Picasso.get().load(courseData.imagen).into(binding.courseDescriptionImage)
        binding.courseDescriptionInscription.setOnClickListener { sendToInscription() }
    }

    /*override fun onBackPressed() {
        finish()
        sendToMain()
    }*/

    private fun sendToInscription(){
        val intent = Intent(applicationContext, InscriptionActivity::class.java)
        intent.putExtra("codAlum", codAlum)
        startActivity(intent)
    }

    private fun goToMain(){
        var intent = Intent(this@CourseDescriptionActivity, MainActivity::class.java)
        intent.putExtra("codAlum", codAlum)
        startActivity(intent)
    }
}