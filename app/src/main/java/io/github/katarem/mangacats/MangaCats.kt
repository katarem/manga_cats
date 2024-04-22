package io.github.katarem.mangacats

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger

class MangaCats: Application(), ImageLoaderFactory  {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
//            .memoryCache {
//                MemoryCache.Builder(this)
//                    .maxSizePercent(0.20)
//                    .build()
//            }
//            .diskCache {
//                DiskCache.Builder()
//                    .directory(cacheDir.resolve("image_cache"))
//                    .maxSizeBytes(5 * 1024 * 1024)
//                    .build()
//            }
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .logger(DebugLogger())
            .respectCacheHeaders(false)
            .build()
    }
}