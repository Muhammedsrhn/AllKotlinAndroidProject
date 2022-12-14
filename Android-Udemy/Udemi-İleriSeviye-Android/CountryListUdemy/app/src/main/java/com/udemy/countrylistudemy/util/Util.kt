package com.udemy.countrylistudemy.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.udemy.countrylistudemy.R

fun ImageView.downloadFromUrl(url: String?) {
    val options = RequestOptions().placeholder(makePlaceHolder(context))
        .error(R.mipmap.ic_launcher)
    Glide.with(context).setDefaultRequestOptions(options)
        .load(url).into(this)

}

fun makePlaceHolder(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }


}

// function for xml
@BindingAdapter("android:ImageDownload")
fun downloadImage(view: ImageView, url: String?) {
    view.downloadFromUrl(url)
}