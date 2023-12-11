package com.longdrink.androidapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.longdrink.androidapp.PaymentsActivity
import com.longdrink.androidapp.ChangeCredentialsActivity
import com.longdrink.androidapp.databinding.FragmentMyAccountBinding

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

        binding = FragmentMyAccountBinding.inflate(layoutInflater)
        disableInputs()
        placeInfo(email, usuario, nombreCompleto)
        binding.myAccountChangePassword.setOnClickListener {
            goToRecovery()
        }
        binding.myAccountViewPayments.setOnClickListener {
            goToPayments()
        }


        return binding.root
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