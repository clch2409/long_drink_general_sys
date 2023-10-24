package com.longdrink.androidapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.longdrink.androidapp.R
import com.longdrink.androidapp.model.Curso
import com.longdrink.androidapp.viewholders.CoursesViewHolder

class CourseAdapter(
    var listadoCursos : List<Curso> = emptyList(),
    private val onItemSelected : (String) -> Unit
) : RecyclerView.Adapter<CoursesViewHolder>()

{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CoursesViewHolder(layoutInflater.inflate(R.layout.list_item_courses, parent, false))
    }

    override fun onBindViewHolder (holder : CoursesViewHolder, position : Int){
        holder.bind(listadoCursos[position])
    }

    override fun getItemCount() = listadoCursos.size



}