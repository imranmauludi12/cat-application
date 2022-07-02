package com.dicoding.mycatapplication.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mycatapplication.R
import com.dicoding.mycatapplication.databinding.ActivityListBreedBinding
import com.dicoding.mycatapplication.core.di.Result
import com.dicoding.mycatapplication.core.presentation.BreedAdapter
import com.dicoding.mycatapplication.core.presentation.ViewModelFactory
import com.dicoding.mycatapplication.detail.DetailBreedActivity
import com.dicoding.mycatapplication.favorite.FavoriteBreedActivity
import dagger.hilt.android.AndroidEntryPoint

class ListBreedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBreedBinding
    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBreedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("cek listActivity", "onCreate atas")

        val adapter = BreedAdapter {
            val intentToDetail = Intent(this, DetailBreedActivity::class.java)
            intentToDetail.putExtra(DetailBreedActivity.BREED_ID, it.id)
            startActivity(intentToDetail)
        }

        binding.rvListBreed.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        binding.rvListBreed.layoutManager = layoutManager

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[ListViewModel::class.java]

        viewModel.listOfCatBreeds.observe(this) {
            when(it) {
                is Result.Loading -> {
                    binding.progressBarList.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBarList.visibility = View.GONE
                    Log.d("cek listActivity", "viewmodel jalan")
                    adapter.submitData(it.data)
                }
                is Result.Error -> {
                    binding.progressBarList.visibility = View.GONE
                    Toast.makeText(this, it.error, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
        Log.d("cek listActivity", "onCreate")
        initAction()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_open_favorite -> {
                val intent = Intent(this, FavoriteBreedActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initAction() {
        val callback = Callback()
        val itemTouchHelper = ItemTouchHelper(callback)

        itemTouchHelper.attachToRecyclerView(binding.rvListBreed)
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
            viewModel.deleteBreed(breed)
        }

    }

}