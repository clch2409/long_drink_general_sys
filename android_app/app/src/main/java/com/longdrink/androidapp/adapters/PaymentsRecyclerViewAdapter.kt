package com.longdrink.androidapp.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.longdrink.androidapp.R
import com.longdrink.androidapp.model.Pago
import com.longdrink.androidapp.viewholders.PaymentsViewHolder

class PaymentsRecyclerViewAdapter(
    val listadoPagos : List<Pago> = emptyList()
)
    :RecyclerView.Adapter<PaymentsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PaymentsViewHolder(layoutInflater.inflate(R.layout.list_item_payments, parent, false))
    }

    override fun getItemCount(): Int {
        return listadoPagos.size
    }

    override fun onBindViewHolder(holder: PaymentsViewHolder, position: Int) {
        holder.bind(listadoPagos[position])
    }

}