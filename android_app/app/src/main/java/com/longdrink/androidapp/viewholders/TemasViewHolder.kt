package com.longdrink.androidapp.viewholders

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.longdrink.androidapp.databinding.ListItemMyCourseClassesBinding
import com.longdrink.androidapp.databinding.ListItemPaymentBinding
import com.longdrink.androidapp.model.Tema

class TemasViewHolder(view : View) : RecyclerView.ViewHolder(view){

    private val binding = ListItemMyCourseClassesBinding.bind(view)

    fun bind (tema : Tema){
        binding.className.text = tema.nombre
        binding.classButton.setOnClickListener { descargarTema(Uri.parse(tema.ficha)) }
    }

    fun descargarTema(uri : Uri){
        val intent = Intent(Intent.ACTION_VIEW, uri)
        ContextCompat.startActivity(this.itemView.context, intent, null)
    }

}