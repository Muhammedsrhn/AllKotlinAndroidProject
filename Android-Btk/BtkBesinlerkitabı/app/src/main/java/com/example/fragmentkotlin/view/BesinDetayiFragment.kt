package com.example.fragmentkotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fragmentkotlin.R
import com.example.fragmentkotlin.util.downloadImage
import com.example.fragmentkotlin.util.makePlaceHolder
import com.example.fragmentkotlin.viewmodel.BesinDetayiViewModel
import kotlinx.android.synthetic.main.fragment_besin_detayi.*
import kotlinx.android.synthetic.main.recycler_row.*


class BesinDetayiFragment : Fragment() {
    private var besinId:Int = 0
    private lateinit var viewModel: BesinDetayiViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besin_detayi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
             besinId = BesinDetayiFragmentArgs.fromBundle(it).besinId
        }

        viewModel = ViewModelProvider(this).get(BesinDetayiViewModel::class.java)
        viewModel.getDataFromRoom(besinId)
        observeLiveData()

    }

    fun observeLiveData() {
        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer { besinler ->
            besinler?.let {
                besinName.text = besinler.besinIsim
                besinKalori.text = besinler.besinKalori
                besinKarbonhidrat.text = besinler.besinKarbonhidrat
                besinProtein.text = besinler.besinProtein
                besinYag.text = besinler.besinYag
                context?.let {
                    besinImage.downloadImage(besinler.besinGorsel, makePlaceHolder(it))
                }
            }
        })
    }
}