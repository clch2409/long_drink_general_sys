package com.longdrink.androidapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.longdrink.androidapp.R
import com.longdrink.androidapp.model.Tema
import com.longdrink.androidapp.viewholders.TemasViewHolder

class TemasRecyclerViewAdapter(
    var listaTemas : List<Tema> = emptyList(),
) : RecyclerView.Adapter<TemasViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TemasViewHolder(layoutInflater.inflate(R.layout.list_item_my_course_classes, parent, false))
    }

    override fun getItemCount(): Int = listaTemas.size

    override fun onBindViewHolder(holder: TemasViewHolder, position: Int) {
        holder.bind(listaTemas[position])
    }

}