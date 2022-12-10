package com.example.btksuperhero

import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recyclerview_row.view.*
import kotlin.math.sign


class RecyclerAdapter(val caracterList:ArrayList<String>,val caracterImages:ArrayList<Int>) : RecyclerView.Adapter<RecyclerAdapter.SuperHeroVH>() {
    class SuperHeroVH(itemView: View) :RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row,parent,false)
        return SuperHeroVH(itemView)
    }

    override fun onBindViewHolder(holder: SuperHeroVH, position: Int) {
        holder.itemView.recyclerviewTextView.text = caracterList.get(position)
        holder.itemView.setOnClickListener{
            val intent = Intent(it.context,TanitimActivity::class.java)
            intent.putExtra("heroName",caracterList.get(position))
            intent.putExtra("heroImage",caracterImages.get(position))
            // val singlenton = SinglentonClass.SelectedHero
            //singlenton.image = caracterImages.get
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return caracterList.size
    }

}