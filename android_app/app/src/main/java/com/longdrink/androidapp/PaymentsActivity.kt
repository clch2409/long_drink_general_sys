package com.longdrink.androidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.longdrink.androidapp.adapters.PaymentsRecyclerViewAdapter
import com.longdrink.androidapp.databinding.ActivityPaymentsBinding
import com.longdrink.androidapp.model.Pago
import com.longdrink.androidapp.retrofit.Api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PaymentsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPaymentsBinding
    private lateinit var adapter : PaymentsRecyclerViewAdapter
    private var codAlum = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentsBinding.inflate(layoutInflater)
        codAlum = intent.getLongExtra("codAlum", codAlum)

        setSupportActionBar(binding.recoveryToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)

        obtenerPagos(codAlum)

        setContentView(binding.root)
    }

    override fun onNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun initUi(listadoPagos : List<Pago> = emptyList()){

        adapter = PaymentsRecyclerViewAdapter(listadoPagos)
        binding.recyclerPayments.setHasFixedSize(true)
        binding.recyclerPayments.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerPayments.adapter = adapter
    }

    private fun obtenerPagos(codAlum : Long = 1){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response : Response<List<Pago>> =
                    Api.apiService.listarPagosAlumno(codAlum)

                Log.i("PAYMENTS", response.body().toString())

                /*TODO: MOSTRAR EN PANTALLA SI ES QUE NO TIENE PAGOS PENDIENTES NI HECHOS*/
            }catch (ex : Exception){
                Log.e("PAYMENTS ACTIVITY", ex.toString())
            }
        }
    }
}