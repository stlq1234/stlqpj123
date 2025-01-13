package com.example.styleiq1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.styleiq1.databinding.FragmentGirisEkraniBinding
import com.example.styleiq1.databinding.FragmentHosgeldinizEkraniBinding


class hosgeldiniz_ekrani : Fragment() {
    private var _binding: FragmentHosgeldinizEkraniBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHosgeldinizEkraniBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_hosgeldiniz_ekrani_to_giris_ekrani2)
        }
        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.action_hosgeldiniz_ekrani_to_kayitol_ekrani2)
        }
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}