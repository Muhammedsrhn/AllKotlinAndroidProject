package com.udemy.brodcastreceiver.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.udemy.brodcastreceiver.R

fun ImageView.downloadImage(url:String?){
    val options = RequestOptions().placeholder(makePlaceholder(context)).error(R.mipmap.ic_launcher)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)

}


fun makePlaceholder(context: Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

//function for data binding using in the xml

@BindingAdapter("android:DownlaodImage")
fun downloadImageForBinding(imageView: ImageView,url:String?){
    imageView.downloadImage(url)
}
