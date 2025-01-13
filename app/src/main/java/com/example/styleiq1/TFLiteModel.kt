package com.example.styleiq1

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.io.FileInputStream

class TFLiteModel(private val context: Context) {

    lateinit var interpreter: Interpreter

    // Modeli başlat
    fun init() {
        interpreter = Interpreter(loadModelFile("model.tflite"))
    }

    // Model dosyasını yükle
    private fun loadModelFile(modelFileName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelFileName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    // Modelden tahmin al
    fun predict(input: FloatArray): FloatArray {
        val output = Array(1) { FloatArray(10) } // Çıkış boyutunu modelinizin detaylarına göre ayarlayın
        interpreter.run(input, output)
        return output[0]
    }

    fun close() {
        interpreter.close()
    }
}
