package com.example.nativeads.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nativeads.R
import com.example.nativeads.utils.Status
import com.example.nativeads.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "mainActivity"

class MainActivity : AppCompatActivity() {
	
	private val mainViewModel by viewModels<MainViewModel>()
	private lateinit var recyclerViewAdapter: RecyclerViewAdapter
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

//		MobileAds.initialize(this)
//		val adLoader: AdLoader = AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
//			.forNativeAd { nativeAd ->
//				val styles =
//					NativeTemplateStyle.Builder().build()
//				val template = findViewById<TemplateView>(R.id.my_template)
//				template.setStyles(styles)
//				template.setNativeAd(nativeAd)
//			}
//			.build()
//
//		adLoader.loadAd(AdRequest.Builder().build())
		
		recyclerViewAdapter = RecyclerViewAdapter(this)
		
		rec_id.apply {
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(this@MainActivity)
			adapter = recyclerViewAdapter
		}
		getPosts()
	}
	
	private fun getPosts() {
		mainViewModel.getPosts().asLiveData().observe(this) { response ->
			Log.d(TAG, "onCreate: Posts Response $response")
			
			when (response.status) {
				Status.LOADING -> {
					Log.d(TAG, "onCreate: Loading")
					Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
				}
				Status.SUCCESS -> {
					Log.d(TAG, "onCreate: Success ${response.data}")
					recyclerViewAdapter.submitList(response.data)
				}
				Status.FAIL -> {
					Log.d(TAG, "onCreate: Error ${response.message}")
				}
			}
		}
	}
	
}