package com.longdrink.androidapp.viewholders

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.longdrink.androidapp.R
import com.longdrink.androidapp.databinding.ListItemPaymentsBinding
import com.longdrink.androidapp.model.Pago

class PaymentsViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private val binding = ListItemPaymentsBinding.bind(view)
    @SuppressLint("ResourceAsColor")
    fun bind(pago : Pago){
        binding.numberPayment.text = pago.codPago.toString()
        binding.dueDate.text = pago.fechaVencimiento.toString()
        if (pago.estado){
            binding.state.text = "Pagado"
            binding.state.setTextColor(R.color.dark_blue)
        }
        else{
            binding.state.text = "Pendiente"
            binding.state.setTextColor(R.color.light_red_error)
        }
        /*TODO: FALTA AGREGAR LA IMAGEN*/
    }
}