package com.example.styleiq1

import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.styleiq1.databinding.FragmentKombinEkraniBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class KombinEkraniFragment : Fragment() {

    private var _binding: FragmentKombinEkraniBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: KombinAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKombinEkraniBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // Farklı kombin öğeleri
        val pantList = listOf(R.drawable.pant1, R.drawable.pant2, R.drawable.pant3, R.drawable.pant4, R.drawable.pant5)
        val jacketList = listOf(R.drawable.jacket1, R.drawable.jacket2, R.drawable.jacket3, R.drawable.jacket4, R.drawable.jacket5)
        val shoesList = listOf(R.drawable.shoes1, R.drawable.shoes2, R.drawable.shoes3, R.drawable.shoes4)
        val shirtList = listOf(R.drawable.shirt1, R.drawable.shirt2, R.drawable.shirt3, R.drawable.shirt6)
        // RecyclerView için adaptörü ayarla
        adapter = KombinAdapter(mutableListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // 1.5 dakika sonra kombinleri yükle
        viewLifecycleOwner.lifecycleScope.launch {
            delay(90_000) // 90 saniye
            val kombinList = mutableListOf<Int>()
            kombinList.add(pantList.random())
            kombinList.add(jacketList.random())
            kombinList.add(shoesList.random())
            kombinList.add(shirtList.random())

            adapter.updateList(kombinList) // Kombinleri adaptöre ekle
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
