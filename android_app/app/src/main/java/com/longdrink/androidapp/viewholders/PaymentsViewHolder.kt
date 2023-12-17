package com.longdrink.androidapp.viewholders

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.longdrink.androidapp.R
import com.longdrink.androidapp.databinding.ListItemPaymentsBinding
import com.longdrink.androidapp.model.Pago
import com.longdrink.androidapp.utils.Utils

class PaymentsViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private val binding = ListItemPaymentsBinding.bind(view)
    @SuppressLint("ResourceAsColor")
    fun bind(pago : Pago){
        binding.paymentDate.text = Utils.devolverFechaString(pago.fechaPago)
        binding.dueDate.text = " " + Utils.devolverFechaString(pago.fechaVencimiento)

        val fechaPago = pago.fechaPago
        val fechaHoy = Utils.devolverFecha(Utils.obtenerFechaHoy())
        val fechaVencimiento = Utils.devolverFecha(pago.fechaVencimiento)

        if (fechaPago == "" && fechaHoy.after(fechaVencimiento)) {
            binding.state.text = "Vencido y Pendiente por pagar"
        }
        else if (fechaPago == "" && fechaHoy.before(fechaVencimiento)){
            binding.state.text = "Pendiente por pagar"
        }
        else if (fechaPago != ""){
            val fechaPagoDate = Utils.devolverFecha(pago.fechaPago)
            if(fechaPagoDate.after(fechaVencimiento)){
                binding.state.text = "Vencido y Pagado"
            }
            else{
                binding.state.text = "Pagado"
            }
        }

        /*TODO: FALTA AGREGAR LA IMAGEN*/
    }
}