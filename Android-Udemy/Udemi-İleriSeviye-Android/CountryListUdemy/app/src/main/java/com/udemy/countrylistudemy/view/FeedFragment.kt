package com.udemy.countrylistudemy.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.udemy.countrylistudemy.R
import com.udemy.countrylistudemy.adapter.FeedAdapter
import com.udemy.countrylistudemy.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {
    private var adapter = FeedAdapter(arrayListOf())
    private lateinit var viewModal: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModal = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModal.refreshData()
        val layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager



        swiperefreshlayout.setOnRefreshListener {
            viewModal.refreshFromInternet()
            swiperefreshlayout.isRefreshing = false
        }
        observeLiveData()
    }

    fun observeLiveData() {
        viewModal.countryList.observe(viewLifecycleOwner, Observer { countries ->

            countries?.let {
                recyclerView.visibility = View.VISIBLE
                loadingProgress.visibility = View.GONE
                errorMessage.visibility = View.GONE
                adapter.updateCountryList(it)
            }

        })

        viewModal.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
                if(loading){
                    loadingProgress.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                }
        })
        viewModal.countryError.observe(viewLifecycleOwner, Observer { error ->
                if(error){
                    loadingProgress.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    errorMessage.visibility = View.VISIBLE
                }

        })
    }



}