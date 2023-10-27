package com.longdrink.androidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.longdrink.androidapp.databinding.ActivityCourseDescriptionBinding
import com.longdrink.androidapp.model.Curso
import com.squareup.picasso.Picasso

class CourseDescriptionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCourseDescriptionBinding
    private lateinit var courseData : Curso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCourseDescriptionBinding.inflate(layoutInflater)

        initUI()

        setContentView(binding.root)
    }

    private fun initUI() {
        courseData = Curso()
        courseData.descripcion = intent.getStringExtra("courseName").toString()
        courseData.mensualidad = intent.getDoubleExtra("price", 0.0)
        courseData.imagen = intent.getStringExtra("image").toString()

        binding.courseDescriptionName.text = courseData.descripcion
        binding.courseDescriptionPrice.text = courseData.mensualidad.toString()
        Picasso.get().load(courseData.imagen).into(binding.courseDescriptionImage)
    }
}