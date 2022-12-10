package com.udemy.brodcastreceiver.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udemy.brodcastreceiver.R
import com.udemy.brodcastreceiver.databinding.ItemRvSubCategoryBinding
import com.udemy.brodcastreceiver.entities.Meals
import com.udemy.brodcastreceiver.util.downloadImage
import kotlinx.android.synthetic.main.item_rv_sub_category.view.img_dish
import kotlinx.android.synthetic.main.item_rv_sub_category.view.tv_dish_name

class SubCategoryAdapter : RecyclerView.Adapter<SubCategoryAdapter.MainVH>() {
    var subMealList = ArrayList<Meals>()

    class MainVH(val view: ItemRvSubCategoryBinding) : RecyclerView.ViewHolder(view.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainVH {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.item_rv_sub_category, parent, false)
        val view = DataBindingUtil.inflate<ItemRvSubCategoryBinding>(
            inflater,
            R.layout.item_rv_sub_category,
            parent,
            false
        )
        return MainVH(view)
    }

    override fun onBindViewHolder(holder: MainVH, position: Int) {
        // holder.itemView.tv_dish_name.text = subMealList.get(position).strMeal
        // holder.itemView.img_dish.downloadImage(subMealList.get(position).strMealThumb)

        holder.view.meal = subMealList.get(position)
    }

    override fun getItemCount(): Int {
        return subMealList.size
    }

    fun updateMealList(newList: List<Meals>) {
        subMealList.clear()
        subMealList.addAll(newList)
        notifyDataSetChanged()
    }


}