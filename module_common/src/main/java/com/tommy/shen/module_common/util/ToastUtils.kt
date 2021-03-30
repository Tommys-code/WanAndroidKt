package com.tommy.shen.module_common.util

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.tommy.shen.module_common.app.BaseApplication

/**
 * Created by Administrator on 2019/9/6.
 */
class ToastUtils {

    companion object {

        private var toast: Toast? = null

        fun showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
            this.showToast(BaseApplication.instance, text, duration)
        }

        fun showToast(
            context: Context?, text: CharSequence,
            duration: Int = Toast.LENGTH_SHORT
        ) {
            Toast.makeText(context, text, duration).show()
        }

        fun showToast(
            context: Context?, text: Int,
            duration: Int = Toast.LENGTH_SHORT
        ) {
            Toast.makeText(context, text, duration).show()
        }

        fun showSnackBar(
            view: View, text: CharSequence,
            duration: Int = Snackbar.LENGTH_SHORT
        ) {
            Snackbar.make(view, text, duration).show()
        }

//        fun getToast(context: Context?, text: CharSequence): Toast {
//            return Toast.makeText(context, text, Toast.LENGTH_SHORT)
//        }
//
//        fun showImageToast(context: Context, resId: Int) {
//            val layout = LayoutInflater.from(context).inflate(
//                R.layout.toast_img, null
//            )
//            val imageView = layout.findViewById<ImageView>(R.id.toast_img)
//            imageView.setImageResource(resId)
//            val toast = getInstanceToast(context)
//            toast.setGravity(
//                Gravity.BOTTOM,
//                0,
//                context.resources.getDimensionPixelOffset(R.dimen.dp22)
//            )
//            toast.duration = Toast.LENGTH_SHORT
//            toast.view = layout
//            toast.show()
//        }
//
//        fun showToast(context: Context, layRes: Int) {
//            val layout = LayoutInflater.from(context).inflate(layRes, null)
//            val toast = getInstanceToast(context)
//            toast.setGravity(
//                Gravity.BOTTOM, 0, context.resources.getDimensionPixelOffset(R.dimen.dp22)
//            )
//            toast.duration = Toast.LENGTH_SHORT
//            toast.view = layout
//            toast.show()
//        }
//
//        fun showToast(context: Context, view: View) {
//            val toast = getInstanceToast(context)
//            toast.setGravity(
//                Gravity.BOTTOM, 0, context.resources.getDimensionPixelOffset(R.dimen.dp50)
//            )
//            toast.duration = Toast.LENGTH_SHORT
//            toast.view = view
//            toast.show()
//        }
//
//        private fun getInstanceToast(context: Context): Toast {
//            if (toast == null) {
//                toast = Toast(context)
//            }
//            return toast as Toast
//        }
    }
}