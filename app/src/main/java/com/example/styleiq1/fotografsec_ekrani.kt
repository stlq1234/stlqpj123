package com.example.styleiq1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.styleiq1.databinding.FragmentFotografsecEkraniBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class fotografsec_ekrani : Fragment() {
    private var _binding: FragmentFotografsecEkraniBinding? = null
    private val binding get() = _binding!!

    private lateinit var photoAdapter: PhotoAdapter
    private val photoList = mutableListOf<Uri>()
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var activityResultLauncher: androidx.activity.result.ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("PhotoAppPrefs", Context.MODE_PRIVATE)
        loadPhotos()

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == android.app.Activity.RESULT_OK) {
                val uri = result.data?.data
                uri?.let {
                    photoList.add(it)
                    photoAdapter.notifyItemInserted(photoList.size - 1)
                    savePhotos() // Yeni fotoğraf eklendiğinde kaydedin.
                }
            } else {
                Toast.makeText(requireContext(), "Fotoğraf seçilemedi!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFotografsecEkraniBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView ve Adapter Ayarı
        photoAdapter = PhotoAdapter(photoList)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = photoAdapter
        }

        // Görsel Seçme İşlemi
        binding.imageView.setOnClickListener { openGallery() }
        binding.yukleButton.setOnClickListener {
            Toast.makeText(requireContext(), "Fotoğraflar başarıyla kaydedildi!", Toast.LENGTH_SHORT).show()
        }
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activityResultLauncher.launch(intent)
    }

    private fun savePhotos() {
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(photoList.map { it.toString() }) // URI'leri String olarak kaydediyoruz.
        editor.putString("photos", json)
        editor.apply()
    }

    private fun loadPhotos() {
        val json = sharedPreferences.getString("photos", null)
        if (json != null) {
            val type = object : TypeToken<List<String>>() {}.type
            val uriStrings: List<String> = Gson().fromJson(json, type)
            photoList.clear()
            for (uriString in uriStrings) {
                val uri = Uri.parse(uriString)
                if (isValidUri(uri)) {
                    photoList.add(uri)
                } else {
                    // Geçersiz URI'yi listeden çıkar.
                    Toast.makeText(requireContext(), "Geçersiz bir URI kaldırıldı.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isValidUri(uri: Uri): Boolean {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            inputStream?.close()
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
