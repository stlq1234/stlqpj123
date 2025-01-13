package com.example.styleiq1

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class KombinAdapter(private val kombinList: MutableList<Int>) :
    RecyclerView.Adapter<KombinAdapter.KombinViewHolder>() {

    inner class KombinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.kombinImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KombinViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kombin, parent, false)
        return KombinViewHolder(view)
    }

    override fun onBindViewHolder(holder: KombinViewHolder, position: Int) {
        Log.d("RecyclerViewBind", "Binding position: $position with resource ID: ${kombinList[position]}")
        holder.imageView.setImageResource(kombinList[position])
    }

    override fun getItemCount(): Int = kombinList.size

    /**
     * Kombin listesini güncellemek için metot.
     */
    fun updateList(newList: List<Int>) {
        kombinList.clear()
        kombinList.addAll(newList)
        notifyDataSetChanged() // Görselleri güncelle
    }
}
