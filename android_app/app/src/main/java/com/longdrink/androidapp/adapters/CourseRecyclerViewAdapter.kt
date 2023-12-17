package com.longdrink.androidapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.longdrink.androidapp.R
import com.longdrink.androidapp.model.Curso
import com.longdrink.androidapp.model.Inscripcion
import com.longdrink.androidapp.model.ListItemCursoTerminado
import com.longdrink.androidapp.viewholders.CoursesViewHolder

class CourseRecyclerViewAdapter(
    var inscripcionesTerminadas : List<Inscripcion> = emptyList(),
) : RecyclerView.Adapter<CoursesViewHolder>()

{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CoursesViewHolder(layoutInflater.inflate(R.layout.list_item_courses, parent, false))
    }

    override fun onBindViewHolder (holder : CoursesViewHolder, position : Int){
        holder.bind(inscripcionesTerminadas[position])
    }

    override fun getItemCount() = inscripcionesTerminadas.size



}