package com.example.styleiq1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.styleiq1.databinding.FragmentHavadurumuEkraniBinding
import com.example.styleiq1.network.ApiClient
import com.example.styleiq1.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class havadurumu_ekrani : Fragment() {
    private var _binding: FragmentHavadurumuEkraniBinding? = null
    private val binding get() = _binding!!
    private val apiKey = "c92cdafe6eef09223c331b2b92a17019"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHavadurumuEkraniBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // Hava durumu kontrol butonu
        binding.btnGetWeather.setOnClickListener {
            val cityName = binding.etCityName.text.toString()
            if (cityName.isNotEmpty()) {
                getWeatherInfo(cityName)
            } else {
                binding.tvWeatherInfo.text = "Lütfen bir şehir adı girin!"
            }
        }

        // Kombin oluşturma butonu
        binding.kombinbutton.setOnClickListener {
            findNavController().navigate(R.id.action_havadurumu_ekrani_to_kombin_ekrani)
        }
    }

    private fun getWeatherInfo(cityName: String) {
        val call = ApiClient.weatherApiService.getWeather(cityName, apiKey)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val weather = response.body()
                    weather?.let {
                        val weatherDescription = it.weather[0].description
                        binding.tvWeatherInfo.text = "Şehir: ${it.name}\n" +
                                "Sıcaklık: ${it.main.temp}°C\n" +
                                "Durum: ${weatherDescription}"

                        // Hava durumuna göre ikon değiştir
                        when (weatherDescription.lowercase()) {
                            "clear sky", "sunny", "açık" -> binding.weatherIcon.setImageResource(R.drawable.sunny)
                            "few clouds", "scattered clouds", "broken clouds", "az bulutlu", "çok bulutlu", "bulutlu", "parçalı bulutlu" -> binding.weatherIcon.setImageResource(
                                R.drawable.cloudy
                            )
                            "shower rain", "rain", "light rain", "yağmurlu", "sağanak yağmur", "sağanak yağmurlu", "sağanak yağış", "yağmur", "az yağmurlu", "çok yağmurlu" -> binding.weatherIcon.setImageResource(
                                R.drawable.rainy
                            )
                        }
                    }
                } else {
                    binding.tvWeatherInfo.text = "Veri alınamadı! Hata Kodu: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                binding.tvWeatherInfo.text = "Hata: ${t.message}"
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
