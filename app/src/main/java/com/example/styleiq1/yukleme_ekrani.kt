package com.example.styleiq1

import android.os.Bundle
import android.text.Layout.Directions
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.styleiq1.databinding.FragmentYuklemeEkraniBinding


class yukleme_ekrani : Fragment() ,PopupMenu.OnMenuItemClickListener {
    private var _binding: FragmentYuklemeEkraniBinding? = null
    private val binding get() = _binding!!
    private lateinit var popup : PopupMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYuklemeEkraniBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener { floatingbuttonTiklandi(it) }
        popup = PopupMenu(requireContext(), binding.floatingActionButton)
        val inflater= popup.menuInflater
        inflater.inflate(R.menu.my_popup_menu,popup.menu)
        popup.setOnMenuItemClickListener(this)
        binding.ilerlebutton.setOnClickListener {
            findNavController().navigate(R.id.action_yukleme_ekrani_to_havadurumu_ekrani)
        }
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
    fun floatingbuttonTiklandi(view: View){
        popup.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId==R.id.yuklemeitem){
            val action = yukleme_ekraniDirections.actionYuklemeEkraniToFotografsecEkrani()
            Navigation.findNavController(requireView()).navigate(action)
        }else if(item?.itemId==R.id.cikisitem){
            val action = yukleme_ekraniDirections.actionYuklemeEkraniToHosgeldinizEkrani()
            Navigation.findNavController(requireView()).navigate(action)
        }
        return true
    }

}