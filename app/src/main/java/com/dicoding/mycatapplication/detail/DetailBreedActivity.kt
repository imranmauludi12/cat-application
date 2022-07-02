package com.dicoding.mycatapplication.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mycatapplication.R
import com.dicoding.mycatapplication.core.domain.BreedEntity
import com.dicoding.mycatapplication.core.presentation.ViewModelFactory
import com.dicoding.mycatapplication.databinding.ActivityDetailBreedBinding

class DetailBreedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBreedBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var breed: BreedEntity
    private var favoriteState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBreedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val intentData = intent?.getIntExtra(BREED_ID, 0)
        if (intentData != null) {
            viewModel.getBreedById(intentData)
        }

        viewModel.breedDetail.observe(this) {
            if (it != null) {
                breed = it
                favoriteState = it.favorite
                binding.apply {
                    inputName.text = it.breedName
                    inputCountry.text = it.country
                    inputOrigin.text = it.origin
                    inputPattern.text = it.pattern
                }
            } else {
                Toast.makeText(this, "Data is empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        if (favoriteState) {
            menu?.getItem(0)?.setIcon(R.drawable.favorite)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                updateFavorite(item)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateFavorite(item: MenuItem) {
        if (favoriteState) {
            viewModel.saveBreedToFavorite(breed, false)
            item.setIcon(R.drawable.favorite_border)
        } else {
            viewModel.saveBreedToFavorite(breed, true)
            item.setIcon(R.drawable.favorite)
        }
    }

    companion object {
        const val BREED_ID = "breed_id_key"
    }
}