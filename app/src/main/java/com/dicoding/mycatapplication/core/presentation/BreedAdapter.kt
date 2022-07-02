package com.dicoding.mycatapplication.core.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mycatapplication.R
import com.dicoding.mycatapplication.databinding.ItemBreedBinding
import com.dicoding.mycatapplication.core.domain.BreedEntity

class BreedAdapter(private val onClickListener: (BreedEntity) -> Unit): RecyclerView.Adapter<BreedAdapter.ListViewHolder>() {

    private val listCatBreed = ArrayList<BreedEntity>()

    fun submitData(newList: List<BreedEntity>) {
        listCatBreed.clear()
        listCatBreed.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_breed, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = listCatBreed[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = listCatBreed.size

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private lateinit var breed: BreedEntity
        private val binding = ItemBreedBinding.bind(itemView)
        fun bind(data: BreedEntity) {
            breed = data
            binding.tvBreedName.text = data.breedName
            binding.tvCountry.text = data.country
            itemView.setOnClickListener { onClickListener(data) }
        }

        fun getBreed(): BreedEntity = breed
    }

}