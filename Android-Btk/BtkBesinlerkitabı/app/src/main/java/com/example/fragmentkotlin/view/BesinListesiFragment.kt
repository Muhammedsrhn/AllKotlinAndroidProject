package com.example.fragmentkotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fragmentkotlin.R
import com.example.fragmentkotlin.adapter.BesinRecyclerAdapter
import com.example.fragmentkotlin.viewmodel.BesinListViewModel
import kotlinx.android.synthetic.main.fragment_besin_listesi.*


class BesinListesiFragment : Fragment() {
    private lateinit var viewModal:BesinListViewModel
    private val adapter = BesinRecyclerAdapter(arrayListOf())
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
        return inflater.inflate(R.layout.fragment_besin_listesi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModal = ViewModelProvider(this).get()
        viewModal.refreshData()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        swiperefreshlayout.setOnRefreshListener {
            loadingPorogress.visibility = View.VISIBLE
            errorText.visibility = View.GONE
            recyclerView.visibility = View.GONE
            viewModal.refreshFromInternet()
            swiperefreshlayout.isRefreshing = false
        }
        observeLiveData()


    }

    fun observeLiveData(){
        viewModal.besinler.observe(viewLifecycleOwner, Observer { besinler ->
            besinler?.let {
                recyclerView.visibility = View.VISIBLE
                errorText.visibility = View.GONE
                loadingPorogress.visibility = View.GONE
                adapter.getUpdateData(besinler)
            }
        })
        viewModal.besinErrorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let{
                if(error){
                        errorText.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        loadingPorogress.visibility = View.GONE
                }
            }
        })
        viewModal.besinLoading.observe(viewLifecycleOwner, Observer {loading->
            loading?.let{
                if(it){
                    loadingPorogress.visibility = View.VISIBLE
                    errorText.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }
            }
        })
    }
}