package com.udemy.mychatapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udemy.mychatapp.databinding.UsersRowBinding
import com.udemy.mychatapp.model.User
import com.udemy.mychatapp.view.ChatActivity
import com.udemy.mychatapp.view.ProfileActivity

class UserAdapter(private val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserVH>() {
    class UserVH(val view: UsersRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.users_row,parent,false)
        val view = UsersRowBinding.inflate(inflater, parent, false)
        return UserVH(view)
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        holder.view.userName.text = userList.get(position).username
        Glide.with(holder.itemView.context).load(userList.get(position).userProfile)
            .into(holder.view.userImage)
        Glide.with(holder.itemView.context).load(userList.get(position).userProfile)
            .into(holder.view.userImage)

        holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ChatActivity::class.java)
                intent.putExtra("userId",userList.get(position).userId)
                intent.putExtra("userName",userList.get(position).username)
                holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}