package com.example.nativeads.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nativeads.PostsApplication
import com.example.nativeads.R
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
	
	//	private val roomViewModel by viewModels<RoomViewModel>()
	private lateinit var recyclerViewAdapter: RecyclerViewAdapter
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		recyclerViewAdapter = RecyclerViewAdapter(this)
		
		rec_id.apply {
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(this@MainActivity)
			adapter = recyclerViewAdapter
		}
		
		getPosts()
		lifecycleScope.launch {
//		showPosts()
		}
	}
	
	private fun getPosts() {
		lifecycleScope.launch {
			mainViewModel.getPosts().collect { response ->
				Log.d(TAG, "onCreate: Posts Response $response")
				when (response.status) {
					Status.LOADING -> {
						Log.d(TAG, "onCreate: Loading")
						withContext(Dispatchers.Main) {
							Toast.makeText(this@MainActivity, "Loading", Toast.LENGTH_SHORT).show()
						}
					}
					Status.SUCCESS -> {
						val posts = response.data
						Log.d(TAG, "onCreate: Success $posts")
//						if (isInternetConnected(this@MainActivity)) {
//							recyclerViewAdapter.submitList(posts)
//							Toast.makeText(this@MainActivity, "Online", Toast.LENGTH_SHORT).show()
//						} else {
//							showPosts()
//							Toast.makeText(this@MainActivity, "Offline", Toast.LENGTH_SHORT).show()
//						}
						showPosts()
					}
					Status.FAIL -> {
						Log.d(TAG, "onCreate: Error ${response.message}")
						Toast.makeText(this@MainActivity, "${response.message}", Toast.LENGTH_SHORT)
							.show()
					}
				}
			}
		}
		
	}
	
	private suspend fun showPosts() {
		mainViewModel.showPosts().collect { posts ->
			if (posts!!.isNotEmpty()) {
				recyclerViewAdapter.submitList(posts)
				recyclerViewAdapter.notifyDataSetChanged()
			}
		}
	}
	
	
	private fun isInternetConnected(context: Context): Boolean {
		val connectivityManager =
			context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			val network = connectivityManager.activeNetwork ?: return false
			val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
			
			return when {
				activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
				activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
				else -> false
			}
		} else {
			val networkInfo = connectivityManager.activeNetworkInfo ?: return false
			return networkInfo.isConnected
		}
		
	}
	
}