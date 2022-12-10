package com.udemy.brodcastreceiver.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udemy.brodcastreceiver.entities.Category
import com.udemy.brodcastreceiver.R
import com.udemy.brodcastreceiver.databinding.ItemRvMainCategoryBinding
import com.udemy.brodcastreceiver.util.downloadImage
import com.udemy.brodcastreceiver.view.DetailsActivity
import kotlinx.android.synthetic.main.item_rv_main_category.view.*

class MainCategoryAdapter : RecyclerView.Adapter<MainCategoryAdapter.MainVH>(), ClickListener {
    val mainCategoryList = ArrayList<Category>()

    class MainVH(val view: ItemRvMainCategoryBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainVH {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.item_rv_main_category, parent, false)
        val view = DataBindingUtil.inflate<ItemRvMainCategoryBinding>(
            inflater,
            R.layout.item_rv_main_category,
            parent,
            false
        )
        return MainVH(view)
    }

    override fun onBindViewHolder(holder: MainVH, position: Int) {

        /*
          holder.itemView.tv_dish_name.text = mainCategoryList.get(position).strCategory

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,DetailsActivity::class.java)
            intent.putExtra("id",mainCategoryList.get(position).id.toString())
            holder.itemView.context.startActivity(intent)
        }

        holder.itemView.img_dish.downloadImage(mainCategoryList.get(position).strCategoryThumb)
         */

        holder.view.category = mainCategoryList.get(position)
        holder.view.listener = this

    }

    override fun getItemCount(): Int {
        return mainCategoryList.size
    }


    fun updateCategoryList(newList: List<Category>) {
        mainCategoryList.clear()
        mainCategoryList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onItemClick(view: View) {
        val intent = Intent(view.context, DetailsActivity::class.java)
        intent.putExtra("id",view.uuid.text.toString())
        view.context.startActivity(intent)
    }


}