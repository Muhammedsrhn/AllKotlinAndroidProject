package com.udemy.countrylistudemy.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.udemy.countrylistudemy.R
import com.udemy.countrylistudemy.databinding.RecFeedRowBinding

import com.udemy.countrylistudemy.model.Country
import com.udemy.countrylistudemy.util.downloadFromUrl
import com.udemy.countrylistudemy.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.rec_feed_row.view.*

class FeedAdapter(val countryList: ArrayList<Country>):
    RecyclerView.Adapter<FeedAdapter.FeedVH>(),ClickListener{
    class FeedVH(val view: RecFeedRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedVH {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.rec_feed_row, parent, false)
        val view = DataBindingUtil.inflate<RecFeedRowBinding>(inflater,R.layout.rec_feed_row,parent,false)
        return FeedVH(view)
    }

    override fun onBindViewHolder(holder: FeedVH, position: Int) {
        holder.view.country = countryList.get(position)
        holder.view.listener = this
/*
        holder.itemView.feedCountry.text = countryList.get(position).countryName
        holder.itemView.feedContinet.text = countryList.get(position).countryRegion

        holder.itemView.feedImage.downloadFromUrl(countryList.get(position).countryFlag)

        holder.itemView.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
 */
        

    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onItemClick(view: View) {
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(view.uuid.text.toString().toInt())
        Navigation.findNavController(view).navigate(action)
    }

}