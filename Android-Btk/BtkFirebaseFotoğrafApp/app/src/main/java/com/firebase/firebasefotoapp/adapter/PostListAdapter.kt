package com.firebase.firebasefotoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.firebasefotoapp.R
import com.firebase.firebasefotoapp.model.Post
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_row.view.*

class PostListAdapter(val posts: ArrayList<Post>) :
    RecyclerView.Adapter<PostListAdapter.PostListVH>() {
    class PostListVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row, parent, false)
        return PostListVH(view)
    }

    override fun onBindViewHolder(holder: PostListVH, position: Int) {
        holder.itemView.userText.text = posts.get(position).email
        holder.itemView.userComment.text = posts.get(position).comment

        /*Glide
            .with(holder.itemView.context)
            .load(posts.get(position).url)
            .centerCrop()
            .into(holder.itemView.userImage)
         */
        Picasso.get()
            .load(posts.get(position).url)
            .into(holder.itemView.userImage)


    }

    override fun getItemCount(): Int {
        return posts.size
    }
}