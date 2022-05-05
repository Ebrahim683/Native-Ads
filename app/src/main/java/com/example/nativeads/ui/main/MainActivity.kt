package com.example.nativeads.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nativeads.PostsApplication
import com.example.nativeads.R
import com.example.nativeads.utils.ConnectionUtils
import com.example.nativeads.utils.Status
import com.example.nativeads.viewmodel.MainViewModel
import com.example.nativeads.viewmodelfactory.PostsViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "mainActivity"

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel> {
        PostsViewModelFactory((application as PostsApplication).repository)
    }
    private lateinit var connectionUtils: ConnectionUtils

    //	private val roomViewModel by viewModels<RoomViewModel>()
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectionUtils = ConnectionUtils(this)
        recyclerViewAdapter = RecyclerViewAdapter(this)

        rec_id.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recyclerViewAdapter
        }


        lifecycleScope.launch {
            getPosts()
//		showPosts()
        }
    }

    private suspend fun getPosts() {
        if (connectionUtils.isConnected()) {
            lifecycleScope.launch {
                mainViewModel.getPosts().collect { response ->
                    Log.d(TAG, "onCreate: Posts Response $response")
                    when (response.status) {
                        Status.LOADING -> {
                            Log.d(TAG, "onCreate: Loading")
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@MainActivity, "Loading", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                        Status.SUCCESS -> {
                            val posts = response.data
                            recyclerViewAdapter.submitList(posts)
                            recyclerViewAdapter.notifyDataSetChanged()
                            Log.d(TAG, "onCreate: Success $posts")
                            Log.d(TAG, "onCreate: Online")
                        }
                        Status.FAIL -> {
                            Log.d(TAG, "onCreate: Error ${response.message}")
                            Toast.makeText(
                                this@MainActivity,
                                "${response.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }
        } else {
            val repository = (application as PostsApplication).repository
            if (repository.getPosts()!!.isNotEmpty()) {
                recyclerViewAdapter.submitList(repository.getPosts())
                Log.d(TAG, "getPosts: Offline")
                Toast.makeText(this, "Offline", Toast.LENGTH_SHORT).show()
            } else {
                Log.d(TAG, "getPosts: no data found")
                Toast.makeText(this, "no data found", Toast.LENGTH_SHORT).show()
            }
        }

    }
}