package com.longdrink.androidapp.viewholders

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.longdrink.androidapp.databinding.ListItemMyCourseClassesBinding
import com.longdrink.androidapp.model.Tema

class TemasViewHolder(view : View) : RecyclerView.ViewHolder(view){

    private val BASE_URL = "http://10.0.2.2:8080/api/v1/tema/descargar_guia?codTema="
    private val binding = ListItemMyCourseClassesBinding.bind(view)

    fun bind (tema : Tema){
        binding.className.text = tema.nombre
        binding.classButton.setOnClickListener { descargarTema(Uri.parse(BASE_URL + tema.codTema)) }
    }

    fun descargarTema(uri : Uri){
        val intent = Intent(Intent.ACTION_VIEW, uri)
        ContextCompat.startActivity(this.itemView.context, intent, null)
    }


}