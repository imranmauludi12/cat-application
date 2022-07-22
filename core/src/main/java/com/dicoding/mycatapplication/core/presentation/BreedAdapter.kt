package com.dicoding.mycatapplication.core.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mycatapplication.core.R
import com.dicoding.mycatapplication.core.databinding.ItemBreedBinding
import com.dicoding.mycatapplication.core.data.local.database.BreedEntity
import com.dicoding.mycatapplication.core.domain.BreedDomain

class BreedAdapter(
    private val dataSets: List<BreedDomain>,
    private val onClickListener: (BreedDomain) -> Unit,
): RecyclerView.Adapter<BreedAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_breed, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = dataSets[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = dataSets.size

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private lateinit var breed: BreedDomain
        private val binding = ItemBreedBinding.bind(itemView)

        fun bind(data: BreedDomain) {
            breed = data
            binding.tvBreedName.text = data.breedName
            binding.tvCountry.text = data.country
            itemView.setOnClickListener { onClickListener(data) }
        }

        fun getBreed(): BreedDomain = breed
    }

}