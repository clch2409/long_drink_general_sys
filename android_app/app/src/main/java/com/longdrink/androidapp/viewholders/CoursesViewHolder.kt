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
import com.longdrink.androidapp.model.ListItemCursoTerminado
import com.squareup.picasso.Picasso

class CoursesViewHolder(view: View, private var codAlum : Long) : RecyclerView.ViewHolder(view) {

    private val binding = ListItemCoursesBinding.bind(view)

    fun bind(cursoTerminado : ListItemCursoTerminado){
        binding.courseName.text = cursoTerminado.nombre
        binding.courseFinishedDate.text = "Terminado el: ${cursoTerminado.fechaTerminadoInscripcion}"
        Picasso.get().load(cursoTerminado.imagen).into(binding.courseImage)
        binding.courseButtonDetails.setOnClickListener { sendToCourseDetail(cursoTerminado) }
    }

    private fun sendToCourseDetail(cursoTerminado : ListItemCursoTerminado){
        val intent = Intent(this.itemView.context, CourseDescriptionActivity::class.java)
        intent.apply {
            putExtra("courseName", cursoTerminado.descripcion)
            putExtra("price", cursoTerminado.mensualidad)
            putExtra("image", cursoTerminado.imagen)
            putExtra("duration", cursoTerminado.duracion)
            putExtra("frecuency", cursoTerminado.frecuencia)
            putExtra("codAlum", codAlum)
        }
        ContextCompat.startActivity(this.itemView.context, intent, null)
    }

    /*private fun sendToInscription(codAlum : Long){
        val intent = Intent(this.itemView.context, InscriptionActivity::class.java)
        intent.apply {
            putExtra("codAlum", codAlum)
        }
        ContextCompat.startActivity(this.itemView.context, intent, null)
    }*/
}