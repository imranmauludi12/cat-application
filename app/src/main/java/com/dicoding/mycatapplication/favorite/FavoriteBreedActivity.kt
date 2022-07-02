package com.dicoding.mycatapplication.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mycatapplication.core.di.Result
import com.dicoding.mycatapplication.core.presentation.BreedAdapter
import com.dicoding.mycatapplication.core.presentation.ViewModelFactory
import com.dicoding.mycatapplication.databinding.ActivityFavoriteBreedBinding
import com.dicoding.mycatapplication.detail.DetailBreedActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteBreedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBreedBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: BreedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBreedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = BreedAdapter {
            val intentToDetail = Intent(this, DetailBreedActivity::class.java)
            intentToDetail.putExtra(DetailBreedActivity.BREED_ID, it.id)
            startActivity(intentToDetail)
        }
        binding.rvFavorite.adapter = adapter

        val linearLayout = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = linearLayout

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
        getFavoriteData()

        initAction()
    }

    private fun getFavoriteData() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteListOfBreed.collect { favorites ->
                    when (favorites) {
                        is Result.Loading -> { binding.progressBarFavorite.visibility = View.VISIBLE }
                        is Result.Success -> {
                            binding.progressBarFavorite.visibility = View.GONE
                            adapter.submitData(favorites.data)
                        }
                        is Result.Error -> {
                            binding.progressBarFavorite.visibility = View.GONE
                            Toast.makeText(this@FavoriteBreedActivity, favorites.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }

    private fun initAction() {
        val callback = Callback()
        val itemTouch = ItemTouchHelper(callback)

        itemTouch.attachToRecyclerView(binding.rvFavorite)
    }

    inner class Callback: ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(0, ItemTouchHelper.RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val breed = (viewHolder as BreedAdapter.ListViewHolder).getBreed()
            viewModel.updateFavorite(breed, false)
        }

    }
}