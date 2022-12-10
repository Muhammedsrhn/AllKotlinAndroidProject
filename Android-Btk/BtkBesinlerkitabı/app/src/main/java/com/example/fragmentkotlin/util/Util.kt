package com.example.fragmentkotlin.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fragmentkotlin.R
import kotlinx.android.synthetic.main.recycler_row.view.*

fun ImageView.downloadImage(url:String?,placeholder:CircularProgressDrawable){
    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun makePlaceHolder(context: Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply({
        strokeWidth = 8f
        start()
    })
}
@BindingAdapter("android:DownloadImage")
fun downloadImage(view: ImageView,url: String?){
    view.downloadImage(url, makePlaceHolder(view.context))
}