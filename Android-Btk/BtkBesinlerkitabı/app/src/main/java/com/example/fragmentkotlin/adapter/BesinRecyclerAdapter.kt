package com.example.fragmentkotlin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentkotlin.R
import com.example.fragmentkotlin.databinding.RecyclerRowBinding
import com.example.fragmentkotlin.model.Besin
import com.example.fragmentkotlin.util.downloadImage
import com.example.fragmentkotlin.util.makePlaceHolder
import com.example.fragmentkotlin.view.BesinListesiFragmentDirections
import kotlinx.android.synthetic.main.recycler_row.view.*

class BesinRecyclerAdapter(val besinList: ArrayList<Besin>) :
    RecyclerView.Adapter<BesinRecyclerAdapter.BesinVH>(),BesinClickListener {

    class BesinVH(var view: RecyclerRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinVH {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.recycler_row, parent, false)
        val view = DataBindingUtil.inflate<RecyclerRowBinding>(
            inflater,
            R.layout.recycler_row,
            parent,
            false
        )
        return BesinVH(view)
    }

    override fun onBindViewHolder(holder: BesinVH, position: Int) {
        /*
        holder.itemView.bsnName.text = besinList.get(position).besinIsim
        holder.itemView.bsnKalori.text = besinList.get(position).besinKalori

        holder.itemView.setOnClickListener {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(besinList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.itemView.bsnImage.downloadImage(besinList.get(position).besinGorsel, makePlaceHolder(holder.itemView.context))

         */
        holder.view.besin = besinList[position]

        holder.view.listener = this



    }

    override fun getItemCount(): Int {
        return besinList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun getUpdateData(newList: List<Besin>) {
        besinList.clear()
        besinList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun besinClicked(view: View) {
        val uuid = view.bsnId.text.toString().toIntOrNull()
        uuid?.let{
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(it)
            Navigation.findNavController(view).navigate(action)
        }
    }


}