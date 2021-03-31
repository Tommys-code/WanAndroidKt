package com.tommy.shen.module_common.util

import android.graphics.Bitmap
import androidx.annotation.NonNull
import androidx.core.util.Preconditions
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.bumptech.glide.util.Util
import java.nio.ByteBuffer
import java.security.MessageDigest

/**
 * @param roundRadius the corner radius (in device-specific pixels).
 * @throws IllegalArgumentException if rounding radius is 0 or less.
 */
class RoundCornerCrop(private val roundRadius: Int) : BitmapTransformation() {

    override fun transform(
        @NonNull pool: BitmapPool,
        @NonNull toTransform: Bitmap,
        outWidth: Int, outHeight: Int
    ): Bitmap {
        return TransformationUtils.roundedCorners(
            pool,
            TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight),
            roundRadius
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other is RoundCornerCrop) {
            val o = other as RoundCornerCrop?
            return roundRadius == o!!.roundRadius
        }
        return false
    }

    override fun hashCode(): Int {
        return Util.hashCode(
            ID.hashCode(),
            Util.hashCode(roundRadius)
        )
    }

    override fun updateDiskCacheKey(@NonNull messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)

        val radiusData = ByteBuffer.allocate(4).putInt(roundRadius).array()
        messageDigest.update(radiusData)
    }

    companion object {
        private const val ID = "com.bumptech.glide.load.resource.bitmap.RoundCornerCrop"
        private val ID_BYTES = ID.toByteArray(Key.CHARSET)
    }

}