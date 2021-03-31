package com.tommy.shen.module_common.util

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Administrator on 2019/10/29.
 */
@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("visibleInVisible")
fun showInVisible(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter(
    value = ["roundCornerCropImageUrl", "roundRadius", "imageRequestListener"],
    requireAll = false
)
fun bindRoundCornerCropImage(
    imageView: ImageView,
    url: String?,
    radius: Float?,
    listener: RequestListener<Drawable?>?
) {
    val options = RequestOptions().transform(RoundCornerCrop(radius?.toInt() ?: 1))
    Glide.with(imageView).load(url).apply(options).listener(listener).into(imageView)
}

@BindingAdapter(value = ["centerCropImageUrl", "imageRequestListener"], requireAll = false)
fun bindCenterCropImage(
    imageView: ImageView,
    url: String?,
    listener: RequestListener<Drawable?>?
) {
    Glide.with(imageView).load(url).centerCrop().listener(listener).into(imageView)
}

@BindingAdapter(value = ["circleImageUrl", "imageRequestListener"], requireAll = false)
fun bindCircleImage(imageView: ImageView, url: String?, listener: RequestListener<Drawable?>?) {
    Glide.with(imageView).load(url).circleCrop().listener(listener).into(imageView)
}

@BindingAdapter(value = ["isSelected"])
fun bindTextViewSelected(textView: TextView, isSelected: Boolean) {
    textView.isSelected = isSelected
}

@BindingAdapter(value = ["textColor"])
fun bindTextViewColor(textView: TextView, textColor: String?) {
    try {
        textView.setTextColor(Color.parseColor(textColor))
    } catch (e: Exception) {

    }
}

@BindingAdapter(value = ["textBlod"])
fun setTextViewBlod(textView: TextView, isBlod: Boolean) {
    textView.typeface = if (isBlod) Typeface.defaultFromStyle(Typeface.BOLD)
    else Typeface.defaultFromStyle(Typeface.NORMAL)
}

@BindingAdapter("backgroundColorString")
fun setViewBackground(view: View, backgroundColor: String?) {
    try {
        view.setBackgroundColor(Color.parseColor(backgroundColor))
    } catch (e: Exception) {

    }
}

@BindingAdapter(value = ["htmlText"])
fun bindHtmlText(textView: TextView, text: String?) {
    if (text == null) return
    textView.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
}


@BindingAdapter(value = ["imageUrl", "imageRequestListener"], requireAll = false)
fun bindImage(imageView: ImageView, url: String?, listener: RequestListener<Drawable?>?) {
    Glide.with(imageView).load(url).listener(listener).into(imageView)
}

@BindingAdapter(
    value = ["cornerImageUrl", "cornerRadius", "cornerType", "imageRequestListener"],
    requireAll = false
)
fun bindCornerImage(
    imageView: ImageView,
    url: String?,
    radius: Float,
    @CornerTransform.CornerType cornerType: Int?,
    listener: RequestListener<Drawable?>?
) {
    val options = RequestOptions().transform(
        CornerTransform(imageView.context, radius, 0f, cornerType ?: CornerTransform.ALL)
    )
    Glide.with(imageView).load(url).apply(options).listener(listener).into(imageView)
}
