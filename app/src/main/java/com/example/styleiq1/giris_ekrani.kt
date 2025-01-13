package com.example.styleiq1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.styleiq1.databinding.FragmentGirisEkraniBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class giris_ekrani : Fragment() {
    private var _binding: FragmentGirisEkraniBinding ? = null
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
        _binding = FragmentGirisEkraniBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.girisyap.setOnClickListener {
            girisyap()

        }

    }
    private fun girisyap() {
        val email = binding.editTextTextEmailAddress.text.toString()
        val password = binding.editTextTextPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                Toast.makeText(requireContext(), "Giriş Başarılı", Toast.LENGTH_SHORT).show()
                val action = giris_ekraniDirections.actionGirisEkrani2ToYuklemeEkrani()
                view?.let {
                    Navigation.findNavController(it).navigate(action)
                }
            }.addOnFailureListener { exception->
                Toast.makeText(requireContext(),exception.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }
        }



        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}