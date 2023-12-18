package com.longdrink.androidapp.viewholders

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.longdrink.androidapp.R
import com.longdrink.androidapp.databinding.ListItemPaymentsBinding
import com.longdrink.androidapp.model.Pago
import com.longdrink.androidapp.utils.Utils
import com.squareup.picasso.Picasso

class PaymentsViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private val binding = ListItemPaymentsBinding.bind(view)
    fun bind(pago : Pago){
        binding.paymentDate.text = Utils.devolverFechaString(pago.fechaPago)
        binding.dueDate.text = " " + Utils.devolverFechaString(pago.fechaVencimiento)

        val fechaPago = pago.fechaPago
        val fechaHoy = Utils.devolverFecha(Utils.obtenerFechaHoy())
        val fechaVencimiento = Utils.devolverFecha(pago.fechaVencimiento)

        if (fechaPago == "" && fechaHoy.after(fechaVencimiento)) {
            binding.state.text = "Vencido y Pendiente por pagar"
            Picasso.get().load("https://cdn.icon-icons.com/icons2/1499/PNG/48/emblemimportant_103451.png").into(binding.paymentStateImage)
        }
        else if (fechaPago == "" && fechaHoy.before(fechaVencimiento)){
            binding.state.text = "Pendiente por pagar"
            Picasso.get().load("https://cdn.icon-icons.com/icons2/1499/PNG/48/emblemimportant_103451.png").into(binding.paymentStateImage)
        }
        else if (fechaPago != ""){
            val fechaPagoDate = Utils.devolverFecha(pago.fechaPago)
            if(fechaPagoDate.after(fechaVencimiento)){
                binding.state.text = "Vencido y Pagado"
                Picasso.get().load("https://cdn.icon-icons.com/icons2/317/PNG/48/sign-check-icon_34365.png").into(binding.paymentStateImage)

            }
            else{
                binding.state.text = "Pagado"
                Picasso.get().load("https://cdn.icon-icons.com/icons2/317/PNG/48/sign-check-icon_34365.png").into(binding.paymentStateImage)
            }
        }

    }
}