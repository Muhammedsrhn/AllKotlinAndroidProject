package com.udemy.lessons

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udemy.lessons.databinding.RecyclerRowBinding
import kotlinx.android.synthetic.main.recycler_row.view.*

class RecycAdapter(val landMarkList: ArrayList<LandMark>) :
    RecyclerView.Adapter<RecycAdapter.LandMarkVH>() {
    class LandMarkVH(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandMarkVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerRowBinding.inflate(inflater,parent,false)
        return  LandMarkVH(binding)
    }

    override fun onBindViewHolder(holder: LandMarkVH, position: Int) {
        holder.itemView.recycler_row_text.text = landMarkList.get(position).name

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,DetailsActivity::class.java)
            //intent.putExtra("landmark",landMarkList.get(position))
            Singleton.chosenLandMark = landMarkList.get(position)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return landMarkList.size
    }
}