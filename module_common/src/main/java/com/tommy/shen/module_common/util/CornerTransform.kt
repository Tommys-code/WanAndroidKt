package com.tommy.shen.module_common.util

import android.content.Context
import android.graphics.*
import androidx.annotation.IntDef
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.bitmap.BitmapResource
import java.nio.ByteBuffer
import java.security.MessageDigest

/**
 * Created by Administrator on 2019/9/23.
 */
class CornerTransform(
    context: Context,
    private val mRadius: Float,
    private val mMargin: Float,
    @CornerType private val cornerType: Int = ALL
) : Transformation<Bitmap> {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
        val radiusData = ByteBuffer.allocate(4).putInt(mRadius.toInt()).array()
        messageDigest.update(radiusData)
    }

    companion object {

        private const val ID = "com.pipikou.funengbao.util.CornerTransform"
        private val ID_BYTES = ID.toByteArray(Key.CHARSET)

        const val ALL = 0
        const val TOP_LEFT = 1
        const val TOP_RIGHT = 2
        const val BOTTOM_LEFT = 3
        const val BOTTOM_RIGHT = 4
        const val TOP = 5
        const val BOTTOM = 6
        const val LEFT = 7
        const val RIGHT = 8
        const val OTHER_TOP_LEFT = 9
        const val OTHER_TOP_RIGHT = 10
        const val OTHER_BOTTOM_LEFT = 11
        const val OTHER_BOTTOM_RIGHT = 12
        const val DIAGONAL_FROM_TOP_LEFT = 13
        const val DIAGONAL_FROM_TOP_RIGHT = 14
    }

    @IntDef(
        ALL, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
        TOP, BOTTOM, LEFT, RIGHT,
        OTHER_TOP_LEFT, OTHER_TOP_RIGHT, OTHER_BOTTOM_LEFT, OTHER_BOTTOM_RIGHT,
        DIAGONAL_FROM_TOP_LEFT, DIAGONAL_FROM_TOP_RIGHT
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class CornerType

    private val mBitmapPool = Glide.get(context).bitmapPool
    private val mDiameter = mRadius * 2

    override fun transform(
        context: Context,
        resource: Resource<Bitmap>,
        outWidth: Int,
        outHeight: Int
    ): Resource<Bitmap> {
        val source = resource.get()

        val width = source.width
        val height = source.height

        val bitmap = mBitmapPool.get(width, height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        drawRoundRect(canvas, paint, width.toFloat(), height.toFloat())
        return BitmapResource.obtain(bitmap, mBitmapPool)!!
    }

    private fun drawRoundRect(canvas: Canvas, paint: Paint, width: Float, height: Float) {
        val right = width - mMargin
        val bottom = height - mMargin

        when (cornerType) {
            ALL -> canvas.drawRoundRect(
                RectF(mMargin, mMargin, right, bottom),
                mRadius,
                mRadius,
                paint
            )
            TOP_LEFT -> drawTopLeftRoundRect(canvas, paint, right, bottom)
            TOP_RIGHT -> drawTopRightRoundRect(canvas, paint, right, bottom)
            BOTTOM_LEFT -> drawBottomLeftRoundRect(canvas, paint, right, bottom)
            BOTTOM_RIGHT -> drawBottomRightRoundRect(canvas, paint, right, bottom)
            TOP -> drawTopRoundRect(canvas, paint, right, bottom)
            BOTTOM -> drawBottomRoundRect(canvas, paint, right, bottom)
            LEFT -> drawLeftRoundRect(canvas, paint, right, bottom)
            RIGHT -> drawRightRoundRect(canvas, paint, right, bottom)
            OTHER_TOP_LEFT -> drawOtherTopLeftRoundRect(canvas, paint, right, bottom)
            OTHER_TOP_RIGHT -> drawOtherTopRightRoundRect(canvas, paint, right, bottom)
            OTHER_BOTTOM_LEFT -> drawOtherBottomLeftRoundRect(canvas, paint, right, bottom)
            OTHER_BOTTOM_RIGHT -> drawOtherBottomRightRoundRect(canvas, paint, right, bottom)
            DIAGONAL_FROM_TOP_LEFT -> drawDiagonalFromTopLeftRoundRect(canvas, paint, right, bottom)
            DIAGONAL_FROM_TOP_RIGHT -> drawDiagonalFromTopRightRoundRect(
                canvas,
                paint,
                right,
                bottom
            )
            else -> canvas.drawRoundRect(
                RectF(mMargin, mMargin, right, bottom),
                mRadius,
                mRadius,
                paint
            )
        }
    }

    private fun drawTopLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(mMargin, mMargin, mMargin + mDiameter, mMargin + mDiameter),
            mRadius, mRadius, paint
        )
        canvas.drawRect(RectF(mMargin, mMargin + mRadius, mMargin + mRadius, bottom), paint)
        canvas.drawRect(RectF(mMargin + mRadius, mMargin, right, bottom), paint)
    }

    private fun drawTopRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(right - mDiameter, mMargin, right, mMargin + mDiameter), mRadius,
            mRadius, paint
        )
        canvas.drawRect(RectF(mMargin, mMargin, right - mRadius, bottom), paint)
        canvas.drawRect(RectF(right - mRadius, mMargin + mRadius, right, bottom), paint)
    }

    private fun drawBottomLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(mMargin, bottom - mDiameter, mMargin + mDiameter, bottom),
            mRadius, mRadius, paint
        )
        canvas.drawRect(RectF(mMargin, mMargin, mMargin + mDiameter, bottom - mRadius), paint)
        canvas.drawRect(RectF(mMargin + mRadius, mMargin, right, bottom), paint)
    }

    private fun drawBottomRightRoundRect(
        canvas: Canvas,
        paint: Paint,
        right: Float,
        bottom: Float
    ) {
        canvas.drawRoundRect(
            RectF(right - mDiameter, bottom - mDiameter, right, bottom), mRadius,
            mRadius, paint
        )
        canvas.drawRect(RectF(mMargin, mMargin, right - mRadius, bottom), paint)
        canvas.drawRect(RectF(right - mRadius, mMargin, right, bottom - mRadius), paint)
    }

    private fun drawTopRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(mMargin, mMargin, right, mMargin + mDiameter), mRadius, mRadius,
            paint
        )
        canvas.drawRect(RectF(mMargin, mMargin + mRadius, right, bottom), paint)
    }

    private fun drawBottomRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(mMargin, bottom - mDiameter, right, bottom), mRadius, mRadius,
            paint
        )
        canvas.drawRect(RectF(mMargin, mMargin, right, bottom - mRadius), paint)
    }

    private fun drawLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(mMargin, mMargin, mMargin + mDiameter, bottom), mRadius, mRadius,
            paint
        )
        canvas.drawRect(RectF(mMargin + mRadius, mMargin, right, bottom), paint)
    }

    private fun drawRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(right - mDiameter, mMargin, right, bottom), mRadius, mRadius,
            paint
        )
        canvas.drawRect(RectF(mMargin, mMargin, right - mRadius, bottom), paint)
    }

    private fun drawOtherTopLeftRoundRect(
        canvas: Canvas,
        paint: Paint,
        right: Float,
        bottom: Float
    ) {
        canvas.drawRoundRect(
            RectF(mMargin, bottom - mDiameter, right, bottom), mRadius, mRadius,
            paint
        )
        canvas.drawRoundRect(
            RectF(right - mDiameter, mMargin, right, bottom), mRadius, mRadius,
            paint
        )
        canvas.drawRect(RectF(mMargin, mMargin, right - mRadius, bottom - mRadius), paint)
    }

    private fun drawOtherTopRightRoundRect(
        canvas: Canvas,
        paint: Paint,
        right: Float,
        bottom: Float
    ) {
        canvas.drawRoundRect(
            RectF(mMargin, mMargin, mMargin + mDiameter, bottom), mRadius, mRadius,
            paint
        )
        canvas.drawRoundRect(
            RectF(mMargin, bottom - mDiameter, right, bottom), mRadius, mRadius,
            paint
        )
        canvas.drawRect(RectF(mMargin + mRadius, mMargin, right, bottom - mRadius), paint)
    }

    private fun drawOtherBottomLeftRoundRect(
        canvas: Canvas,
        paint: Paint,
        right: Float,
        bottom: Float
    ) {
        canvas.drawRoundRect(
            RectF(mMargin, mMargin, right, mMargin + mDiameter), mRadius, mRadius,
            paint
        )
        canvas.drawRoundRect(
            RectF(right - mDiameter, mMargin, right, bottom), mRadius, mRadius,
            paint
        )
        canvas.drawRect(RectF(mMargin, mMargin + mRadius, right - mRadius, bottom), paint)
    }

    private fun drawOtherBottomRightRoundRect(
        canvas: Canvas, paint: Paint, right: Float,
        bottom: Float
    ) {
        canvas.drawRoundRect(
            RectF(mMargin, mMargin, right, mMargin + mDiameter), mRadius, mRadius,
            paint
        )
        canvas.drawRoundRect(
            RectF(mMargin, mMargin, mMargin + mDiameter, bottom), mRadius, mRadius,
            paint
        )
        canvas.drawRect(RectF(mMargin + mRadius, mMargin + mRadius, right, bottom), paint)
    }

    private fun drawDiagonalFromTopLeftRoundRect(
        canvas: Canvas, paint: Paint, right: Float,
        bottom: Float
    ) {
        canvas.drawRoundRect(
            RectF(mMargin, mMargin, mMargin + mDiameter, mMargin + mDiameter),
            mRadius, mRadius, paint
        )
        canvas.drawRoundRect(
            RectF(right - mDiameter, bottom - mDiameter, right, bottom), mRadius,
            mRadius, paint
        )
        canvas.drawRect(RectF(mMargin, mMargin + mRadius, right - mDiameter, bottom), paint)
        canvas.drawRect(RectF(mMargin + mDiameter, mMargin, right, bottom - mRadius), paint)
    }

    private fun drawDiagonalFromTopRightRoundRect(
        canvas: Canvas, paint: Paint, right: Float,
        bottom: Float
    ) {
        canvas.drawRoundRect(
            RectF(right - mDiameter, mMargin, right, mMargin + mDiameter), mRadius,
            mRadius, paint
        )
        canvas.drawRoundRect(
            RectF(mMargin, bottom - mDiameter, mMargin + mDiameter, bottom),
            mRadius, mRadius, paint
        )
        canvas.drawRect(RectF(mMargin, mMargin, right - mRadius, bottom - mRadius), paint)
        canvas.drawRect(RectF(mMargin + mRadius, mMargin + mRadius, right, bottom), paint)
    }

}