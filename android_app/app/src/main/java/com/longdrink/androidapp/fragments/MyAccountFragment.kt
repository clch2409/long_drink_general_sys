package com.longdrink.androidapp.fragments

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.longdrink.androidapp.PaymentsActivity
import com.longdrink.androidapp.ChangeCredentialsActivity
import com.longdrink.androidapp.databinding.FragmentMyAccountBinding
import com.longdrink.androidapp.model.Asistencia
import com.longdrink.androidapp.model.Inscripcion
import com.longdrink.androidapp.model.MarcarAsistencia
import com.longdrink.androidapp.model.Mensaje
import com.longdrink.androidapp.retrofit.Api
import com.longdrink.androidapp.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyAccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyAccountFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentMyAccountBinding
    private var email = ""
    private var usuario = ""
    private var nombreCompleto = ""
    private var codAlum = 0L
    private lateinit var fechaActual : String
    private lateinit var horaActual: String
    private lateinit var inscripcion : Inscripcion
    private var asistenciaMarcada = false
    private var permitirAsistencia = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        email = requireArguments().getString("email")!!
        usuario = requireArguments().getString("usuario")!!
        nombreCompleto = requireArguments().getString("nombreCompleto")!!
        codAlum = requireArguments().getLong("codAlum")
        inscripcion = requireArguments().getSerializable("inscripcion") as Inscripcion
        fechaActual = Utils.obtenerFechaHoy()
        horaActual = Utils.obtenerHoraActual()
        permitirAsistencia = habilitarAsistencia()

        binding = FragmentMyAccountBinding.inflate(layoutInflater)
        disableInputs()
        placeInfo(email, usuario, nombreCompleto)
        comprobarAsistencia(fechaActual, inscripcion.codInscripcion)

        binding.myAccountChangePassword.setOnClickListener {
            goToRecovery()
        }
        binding.myAccountViewPayments.setOnClickListener {
            goToPayments()
        }

        if (!permitirAsistencia){
            binding.myAccountAssistence.isEnabled = false
        }
        else{
            binding.myAccountAssistence.setOnClickListener {

                val dialog : AlertDialog.Builder?
                val horaActual = Utils.obtenerHoraActual()
                if (asistenciaMarcada){
                    dialog = Utils.showDialog("Marcar Asistencia",
                        "Usted ya ha marcado su asistencia",
                        this.requireContext())
                    dialog.apply {
                        setNeutralButton("OK"){ _: DialogInterface, _: Int ->

                        }
                    }
                }
                else{
                    dialog = Utils.showDialog("Marcar Asistencia",
                        "Marcar Asistencia al\n " +
                                "Curso: ${inscripcion.seccion.curso.nombre}" +
                                "\n" +
                                "Sección: ${inscripcion.seccion.nombre}" +
                                "\n" +
                                "Hora de Inicio: ${inscripcion.seccion.turno.horaInicio}" +
                                "\n"+
                                "Hora de Asistencia: $horaActual" +
                                "\n" ,
                        this.requireContext())
                    dialog.apply {
                        setPositiveButton("SI") { _: DialogInterface, _: Int ->
                            val marcarAsistencia = MarcarAsistencia(inscripcion.codInscripcion, Utils.obtenerFechaHoy(), Utils.obtenerHoraActual(), true)
                            marcarAsistencia(marcarAsistencia)
                        }
                        setNegativeButton("NO") { _: DialogInterface, _: Int ->
                        }
                    }
                }

                dialog.create()
                dialog.show()
            }
        }

        return binding.root
    }

    private fun marcarAsistencia(marcarAsistencia: MarcarAsistencia) {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response : Response<Asistencia> =
                    Api.apiService.marcarAsistencia(marcarAsistencia)


                activity?.runOnUiThread{
                    comprobarAsistencia(fechaActual, inscripcion.codInscripcion)
                }
            }
            catch (ex : Exception){
                Log.e("MARCAR ASISTENCIA", ex.toString())
            }
        }
    }

    private fun placeInfo(vararg datos : String){
        binding.myAccountEmail.hint = datos[0]
        binding.myAccountUsername.hint = datos[1]
        binding.myAccountFullname.hint = datos[2]
    }

    /*private fun showSnackbar(mensaje : String){
        activity?.runOnUiThread{ Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_LONG).show() }
    }*/

    private fun disableInputs(){
        binding.myAccountEmail.isEnabled = false
        binding.myAccountUsername.isEnabled = false
        binding.myAccountFullname.isEnabled = false
    }

    private fun goToRecovery(){
        startActivity(Intent(activity, ChangeCredentialsActivity::class.java))
    }

    private fun goToPayments(){
        var intent = Intent(activity, PaymentsActivity::class.java)
        intent.putExtra("codAlum", codAlum)
        startActivity(intent)
    }

    /**
    * Comprueba si permite marcar asistencia o no, dependiendo del día de semana
    * [diaSemanaActual] es el número del día en la semana (domingo = 1, lunes = 2 ...)
    * */
    private fun habilitarAsistencia() : Boolean{
        val diaSemanaActual = Utils.obtenerDiaSemana()
        if (inscripcion.fechaTerminado == null && inscripcion.seccion.estado){
            when (inscripcion.seccion.curso.frecuencia){
                "Diario" -> {
                    if (diaSemanaActual == 1 || diaSemanaActual == 7){
                        return false
                    }
                    return true
                }
                "Interdiario" -> {
                    if (diaSemanaActual == 1 || diaSemanaActual == 3 || diaSemanaActual == 5 || diaSemanaActual == 6){
                        return false
                    }
                    return true
                }
                "Sabados - Domingos" -> {
                    if (diaSemanaActual != 1 && diaSemanaActual != 7){
                        return false
                    }
                    return true
                }
            }
        }
        return false
    }

    private fun comprobarAsistencia(fecha: String, codInscripcion : Long){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val responseAsistencia : Response<Mensaje> =
                    Api.apiService.comprobarAsistencia(fecha, codInscripcion)

                if (responseAsistencia.code() == 200){
                    asistenciaMarcada = true
                }

            }catch (ex : Exception){
                Log.e("COMPROBAR ASISTENCIA", ex.toString())
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyAccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyAccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}