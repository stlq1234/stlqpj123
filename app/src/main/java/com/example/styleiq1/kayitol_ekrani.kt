package com.example.styleiq1

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.styleiq1.databinding.FragmentGirisEkraniBinding
import com.example.styleiq1.databinding.FragmentKayitolEkraniBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class kayitol_ekrani : Fragment() {
    private var _binding: FragmentKayitolEkraniBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKayitolEkraniBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }


        // Giyim Spinner
        val giyim = listOf("Klasik", "Şık", "Spor", "Maskülen", "Feminen")
        val giyimAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, giyim)
        giyimAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnergiyim.adapter = giyimAdapter
        binding.spinnergiyim.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedGiyim = giyim[position]
                Toast.makeText(requireContext(), "Seçilen Giyim: $selectedGiyim", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(requireContext(), "Lütfen bir seçim yapınız", Toast.LENGTH_SHORT).show()
            }
        }

        // Cinsiyet Spinner
        val cinsiyet = listOf("Kadın", "Erkek")
        val cinsiyetAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cinsiyet)
        cinsiyetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnercinsiyet.adapter = cinsiyetAdapter
        binding.spinnercinsiyet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCinsiyet = cinsiyet[position]
                Toast.makeText(requireContext(), "Seçilen Cinsiyet: $selectedCinsiyet", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(requireContext(), "Lütfen bir seçim yapınız", Toast.LENGTH_SHORT).show()
            }
        }

        // Kayıt Ol Butonu
        binding.kaydolButton.setOnClickListener {
            kaydol()
        }
    }

    private fun kaydol() {
        val email = binding.editTextTextEmailAddress2.text.toString()
        val password = binding.editTextTextPassword2.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Kayıt Başarılı", Toast.LENGTH_SHORT).show()
                        val action = kayitol_ekraniDirections.actionKayitolEkrani2ToYuklemeEkrani()
                        view?.let {
                            Navigation.findNavController(it).navigate(action)
                        }

                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(requireContext(), "E-posta ve şifre boş bırakılamaz", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

