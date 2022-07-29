package com.dicoding.mycatapplication.list

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mycatapplication.R
import com.dicoding.mycatapplication.databinding.ActivityListBreedBinding
import com.dicoding.mycatapplication.core.util.Result
import com.dicoding.mycatapplication.core.presentation.BreedAdapter
import com.dicoding.mycatapplication.detail.DetailBreedActivity
import com.dicoding.mycatapplication.setting.SettingActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListBreedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBreedBinding
    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBreedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvListBreed.layoutManager = layoutManager

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.listOfCatBreeds
                    .collect { result ->
                    when (result) {
                        is Result.Loading -> binding.progressBarList.visibility = View.VISIBLE
                        is Result.Success -> {
                            binding.progressBarList.visibility = View.GONE
                            val dataSets = result.data
                            if (dataSets != null) {

                                val adapter = BreedAdapter(dataSets = dataSets) { entity ->
                                    val intentToDetail = Intent(this@ListBreedActivity, DetailBreedActivity::class.java)
                                    intentToDetail.putExtra(DetailBreedActivity.BREED_ID, entity.id)
                                    startActivity(intentToDetail)
                                }
                                binding.rvListBreed.adapter = adapter
                            }
                        }
                        is Result.Error -> {
                            binding.progressBarList.visibility = View.GONE
                            Toast.makeText(this@ListBreedActivity, result.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        initAction()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_open_favorite -> {
                installModuleFavorite()
                true
            }
            R.id.action_open_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveToFavoriteModule() {
//        val favActivity = Class.forName("com.dicoding.mycatapplication.favorite.FavoriteBreedActivity")
//        val intent = Intent(this, favActivity)
        val uri = Uri.parse("mycatapplication://favorite")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun installModuleFavorite() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val moduleFavorite = "favorite"
        if (splitInstallManager.installedModules.contains(moduleFavorite)) {
            moveToFavoriteModule()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleFavorite)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Log.d(TAG, "success install")
                    moveToFavoriteModule()
                }
                .addOnFailureListener { exc ->
                    Log.d(TAG, "message: ${exc.message.toString()}")
                    Log.d(TAG, "cause: ${exc.cause.toString()}")
                    Toast.makeText(this, "Module cannot be installed!", Toast.LENGTH_SHORT).show()
                }
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

    companion object {
        private const val TAG = "cek ListActivity"
    }
}