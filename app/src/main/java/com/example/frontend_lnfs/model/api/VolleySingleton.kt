package com.example.frontend_lnfs.model.api


import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import com.example.frontend_lnfs.App


class VolleySingleton {
    companion object {
        @Volatile
        private var INSTANCE: VolleySingleton? = null

        fun getInstance() =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: VolleySingleton().also {
                            INSTANCE = it
                        }
                }
    }

    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue,
            object : ImageLoader.ImageCache {
                private val cache = LruCache<String, Bitmap>(20)
                override fun getBitmap(url: String): Bitmap {
                    return cache.get(url)
                }

                override fun putBitmap(url: String, bitmap: Bitmap) {
                    cache.put(url, bitmap)
                }
            })
    }
    val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(App.getContext())
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}