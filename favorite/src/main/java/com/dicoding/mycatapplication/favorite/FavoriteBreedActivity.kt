package com.dicoding.mycatapplication.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mycatapplication.core.di.FavoriteModuleDependencies
import com.dicoding.mycatapplication.core.domain.BreedDomain
import com.dicoding.mycatapplication.core.util.Result
import com.dicoding.mycatapplication.core.presentation.BreedAdapter
import com.dicoding.mycatapplication.detail.DetailBreedActivity
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteBreedActivity : AppCompatActivity() {
    private lateinit var adapter: BreedAdapter
    private lateinit var rvFavorite: RecyclerView

    @Inject lateinit var viewModelFactory: VIewModelFactory
    private val viewModel: FavoriteViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_breed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvFavorite = findViewById(R.id.rv_favorite)
        val linearLayout = LinearLayoutManager(this)
        rvFavorite.layoutManager = linearLayout

        getFavoriteData()

        initAction()
    }

    private fun getFavoriteData() {
        val progressBar: ProgressBar = findViewById(R.id.progressBar_favorite)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteListOfBreed.collect { favorites ->
                    when (favorites) {
                        is Result.Loading -> { progressBar.visibility = View.VISIBLE }
                        is Result.Success -> {
                            progressBar.visibility = View.GONE
                            adapter = BreedAdapter(favorites.data) {
                                onClickListener(it)
                            }
                            rvFavorite.adapter = adapter
                        }
                        is Result.Error -> {
                            progressBar.visibility= View.GONE
                            Toast.makeText(this@FavoriteBreedActivity, favorites.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }

    private fun onClickListener(it: BreedDomain) {
        val intentToDetail = Intent(this, DetailBreedActivity::class.java)
        intentToDetail.putExtra(DetailBreedActivity.BREED_ID, it.id)
        intentToDetail.putExtra(DetailBreedActivity.INTENT_ORIGIN_FAVORITE, true)
        startActivity(intentToDetail)
    }

    private fun initAction() {
        val callback = Callback()
        val itemTouch = ItemTouchHelper(callback)

        itemTouch.attachToRecyclerView(rvFavorite)
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