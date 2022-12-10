package com.udemy.countrylistudemy.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.udemy.countrylistudemy.R
import com.udemy.countrylist.viewmodel.CountryViewModel
import com.udemy.countrylistudemy.databinding.FragmentCountryBinding
import com.udemy.countrylistudemy.util.downloadFromUrl
import kotlinx.android.synthetic.main.fragment_country.*


class CountryFragment : Fragment() {
    private var uuid: Int? = null
    private lateinit var viewModel: CountryViewModel
    private lateinit var dataBinding:FragmentCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_country,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)

        arguments?.let {
            uuid = CountryFragmentArgs.fromBundle(it).uuid
        }
        viewModel.getDataFromRoom(uuid!!)

        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                /*
                      name.text = it.countryName
                capital.text = it.countryCapital
                region.text = it.countryRegion
                currency.text = it.countryCurrency
                laguage.text = it.countryLanguage
                currency.text = it.countryCurrency
                flag.downloadFromUrl(it.countryFlag)
                 */
                dataBinding.country = it
            }
        })
    }


}