package com.longdrink.androidapp.viewholders

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.longdrink.androidapp.CourseDescriptionActivity
import com.longdrink.androidapp.InscriptionActivity
import com.longdrink.androidapp.databinding.FragmentCoursesBinding
import com.longdrink.androidapp.databinding.ListItemCoursesBinding
import com.longdrink.androidapp.model.Curso
import com.squareup.picasso.Picasso

class CoursesViewHolder(view: View, private var codAlum : Long) : RecyclerView.ViewHolder(view) {

    private val binding = ListItemCoursesBinding.bind(view)

    fun bind(curso : Curso){
        binding.courseName.text = curso.descripcion
        Picasso.get().load(curso.imagen).into(binding.courseImage)
        binding.courseButtonDetails.setOnClickListener { sendToCourseDetail(curso) }
        binding.courseButtonInscription.setOnClickListener { sendToInscription(codAlum) }
    }

    private fun sendToCourseDetail(curso : Curso){
        val intent = Intent(this.itemView.context, CourseDescriptionActivity::class.java)
        intent.apply {
            putExtra("courseName", curso.descripcion)
            putExtra("price", curso.mensualidad)
            putExtra("image", curso.imagen)
            putExtra("duration", curso.duracion)
            putExtra("frecuency", curso.frecuencia)
            putExtra("codAlum", codAlum)
        }
        ContextCompat.startActivity(this.itemView.context, intent, null)
    }

    private fun sendToInscription(codAlum : Long){
        val intent = Intent(this.itemView.context, InscriptionActivity::class.java)
        intent.apply {
            putExtra("codAlum", codAlum)
        }
        ContextCompat.startActivity(this.itemView.context, intent, null)
    }
}