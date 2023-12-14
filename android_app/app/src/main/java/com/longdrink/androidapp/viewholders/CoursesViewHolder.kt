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
import com.longdrink.androidapp.model.Inscripcion
import com.longdrink.androidapp.model.ListItemCursoTerminado
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CoursesViewHolder(view: View, private var inscripcion : Inscripcion) : RecyclerView.ViewHolder(view) {

    private val binding = ListItemCoursesBinding.bind(view)

    fun bind(inscripcion : Inscripcion){
        val formato = SimpleDateFormat("yyyy-MM-dd")
        val fechaFinal = formato.parse(inscripcion.fechaTerminado)
        formato.applyPattern("dd-MM-yyyy")
        val fechaFinalFormateada = formato.format(fechaFinal)

        binding.courseName.text = inscripcion.seccion.curso.nombre
        binding.courseFinishedDate.text = "Terminado el: $fechaFinalFormateada"
        Picasso.get().load(inscripcion.seccion.curso.imagen).into(binding.courseImage)
        binding.courseButtonDetails.setOnClickListener { sendToCourseDetail(inscripcion) }
    }

    private fun sendToCourseDetail(inscripcion: Inscripcion){
        val intent = Intent(this.itemView.context, CourseDescriptionActivity::class.java)
        intent.apply {
            putExtra("inscripcion",inscripcion)
            /*putExtra("courseName", cursoTerminado.descripcion)
            putExtra("image", cursoTerminado.imagen)
            putExtra("fechaInicio", cursoTerminado.fechaInicioInscripcion)
            putExtra("estado", cursoTerminado.estadoInscripcion)
            putExtra("fechaTerminado", cursoTerminado.fechaTerminadoInscripcion)*/
            putExtra("codAlum", inscripcion.codAlum)
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