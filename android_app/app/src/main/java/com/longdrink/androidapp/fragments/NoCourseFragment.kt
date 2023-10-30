package com.longdrink.androidapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.longdrink.androidapp.R
import com.longdrink.androidapp.databinding.FragmentCoursesBinding
import com.longdrink.androidapp.databinding.FragmentNoCourseBinding
import com.squareup.picasso.Picasso


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NoCourseFragment : Fragment() {

    private lateinit var binding : FragmentNoCourseBinding
    private var param1: String? = null
    private var param2: String? = null

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

        binding = FragmentNoCourseBinding.inflate(inflater)

        Picasso.get().load("https://iconos8.es/icon/gCWKzO-Ww9oS/cara-pensante").into(binding.noCourseImage)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NoCourseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NoCourseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}