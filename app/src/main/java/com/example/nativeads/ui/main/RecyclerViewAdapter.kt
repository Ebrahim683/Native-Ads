package com.example.nativeads.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nativeads.R
import com.example.nativeads.data.model.PostModelItem
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

private const val TAG = "recyclerViewAdapter"

class RecyclerViewAdapter(context: Context) : ListAdapter<PostModelItem, RecyclerView.ViewHolder>(
	DIFF_UTIL
) {
	var context: Context? = context
	val ITEM_TEXT = 0
	val ITEM_ADS = 1
	
	class TextHolder(itemView: View) :
		RecyclerView.ViewHolder(itemView) {
		val id = itemView.findViewById<TextView>(R.id.sample_id)
		val title = itemView.findViewById<TextView>(R.id.sample_title)
		val body = itemView.findViewById<TextView>(R.id.sample_body)
		fun bind(postModelItem: PostModelItem) {
			id.text = "ID : ${postModelItem.id}"
			title.text = "Title : ${postModelItem.title}"
			body.text = "Body : ${postModelItem.body}"
			
		}
	}
	
	class AdsHolder(view: View) : RecyclerView.ViewHolder(view) {
		val myTemplateView = view.findViewById<TemplateView>(R.id.my_template)!!
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		
		return if (viewType == ITEM_TEXT) {
			val viewT =
				LayoutInflater.from(parent.context).inflate(R.layout.rec_layout, parent, false)
			TextHolder(viewT)
		} else {
			val viewA =
				LayoutInflater.from(parent.context).inflate(R.layout.ad_layout, parent, false)
			AdsHolder(viewA)
		}
		
	}
	
	override fun getItemViewType(position: Int): Int {
		return if (position != 0 && position % 4 == 0) {
			ITEM_ADS
		} else ITEM_TEXT
	}
	
	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		
		when (getItemViewType(position)) {
			ITEM_TEXT -> {
				val tHolder = TextHolder(holder.itemView)
				tHolder.bind(getItem(position))
			}
			ITEM_ADS -> {
				val aHolder = AdsHolder(holder.itemView)
				MobileAds.initialize(context)
				val adLoader: AdLoader =
					AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
						.forNativeAd { nativeAd ->
							val styles =
								NativeTemplateStyle.Builder().build()
							aHolder.myTemplateView.setStyles(styles)
							aHolder.myTemplateView.setNativeAd(nativeAd)
						}
						.build()
				
				adLoader.loadAd(AdRequest.Builder().build())
				
			}
		}
	}
	
	companion object {
		val DIFF_UTIL = object : DiffUtil.ItemCallback<PostModelItem>() {
			override fun areItemsTheSame(oldItem: PostModelItem, newItem: PostModelItem): Boolean {
				return oldItem.title == newItem.title
			}
			
			override fun areContentsTheSame(
				oldItem: PostModelItem,
				newItem: PostModelItem
			): Boolean {
				return oldItem.title == newItem.title
			}
			
		}
	}
}
