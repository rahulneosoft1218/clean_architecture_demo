package com.rahul.cleandemo.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.rahul.cleandemo.BuildConfig
import com.rahul.cleandemo.R
import com.rahul.cleandemo.base.BaseViewModel

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("bindImage")
    fun setBindImage(imageView: ImageView, imageUrl: String?) {
        if (!TextUtils.isEmpty(imageUrl)) {
            if (BuildConfig.DEBUG) {
                Log.d("imageUrl", imageUrl!!)
            }
        }

        val context: Context = imageView.context ?: return
        val diskCache = DiskCache.Builder()
            .directory(context.cacheDir.resolve("image_cache"))
            .build()
        val memoryCache = MemoryCache.Builder(context)
            .build()
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .diskCachePolicy(CachePolicy.ENABLED)
            .placeholder(R.drawable.placeholder_img)
            .error(R.drawable.placeholder_img)
            .target(imageView)
            .build()
        val imageLoader = ImageLoader.Builder(context)
            .memoryCache(memoryCache)
            .diskCache(diskCache)
            .build()

        imageLoader.enqueue(request)

    }


    @JvmStatic
    @BindingAdapter("onPullToRefresh", "onPullToRefreshStatus", "onPullToRefreshId")
    fun setSwipeRefresh(
        swipeRefreshLayout: SwipeRefreshLayout,
        vm: BaseViewModel,
        refreshStatus: Boolean,
        onPullToRefreshId: Int,
    ) {
        swipeRefreshLayout.isRefreshing = refreshStatus
        swipeRefreshLayout.setOnRefreshListener {
            vm.onPullToRefresh(onPullToRefreshId)
        }

    }
}