package com.dicoding.mycatapplication.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.mycatapplication.R
import com.dicoding.mycatapplication.core.domain.BreedDomain
import com.dicoding.mycatapplication.databinding.ActivityDetailBreedBinding
import com.dicoding.mycatapplication.favorite.FavoriteBreedActivity
import com.dicoding.mycatapplication.favorite.FavoriteBreedActivity.Companion.INTENT_ORIGIN_FAVORITE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailBreedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBreedBinding
    private lateinit var breed: BreedDomain
    private var favoriteState: Boolean = false
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBreedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentData = intent?.getIntExtra(BREED_ID, 0)
        if (intentData != null) {
            viewModel.getBreedById(intentData)
        }

        viewModel.breedDetail.observe(this) {
            if (it != null) {
                breed = it
                favoriteState = it.isFavorite
                binding.apply {

                    if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                        inputName.setTextColor(resources.getColor(R.color.grey))
                        inputCountry.setTextColor(resources.getColor(R.color.grey))
                        inputOrigin.setTextColor(resources.getColor(R.color.grey))
                        inputPattern.setTextColor(resources.getColor(R.color.grey))
                    }

                    inputName.text = it.breedName
                    inputCountry.text = it.country
                    inputOrigin.text = it.origin
                    inputPattern.text = it.pattern
                }
            } else {
                Toast.makeText(this, "Data is empty!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.deleteButton.setOnClickListener(this::deleteData)
        val isComeFromFavoriteActivity = intent?.getBooleanExtra(INTENT_ORIGIN_FAVORITE, false)
        if (isComeFromFavoriteActivity == true) {
            binding.deleteButton.visibility = View.GONE
        }
    }

    private fun deleteData(view: View) {
        viewModel.deleteBreed(breed)
        finish()
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