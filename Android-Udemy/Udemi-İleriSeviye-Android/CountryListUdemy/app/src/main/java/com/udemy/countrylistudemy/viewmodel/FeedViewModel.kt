package com.udemy.countrylistudemy.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.udemy.countrylistudemy.model.Country
import com.udemy.countrylistudemy.service.CountryAPIservice
import com.udemy.countrylistudemy.service.CountryDatabase
import com.udemy.countrylistudemy.util.CustomSharedPrefences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {
    val countryList = MutableLiveData<List<Country>>()
    val countryLoading = MutableLiveData<Boolean>()
    val countryError = MutableLiveData<Boolean>()
    private val apiService = CountryAPIservice()
    private val disposable = CompositeDisposable()
    private val customSharedPrefence = CustomSharedPrefences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    fun refreshData() {
        //countryLoading.value = false
        //countryError.value = false
        val updateTime = customSharedPrefence.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime.toLong() < refreshTime) {
            getDataFromRoom()
        } else {
            getDataFromAPI()
        }
    }

    fun getDataFromAPI() {
        countryLoading.value = true
        countryError.value = false
        disposable.add(
            apiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        storeInSqlite(t)
                        Toast.makeText(getApplication(), "From Intenet", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })


        )

    }


    fun refreshFromInternet() {
        getDataFromAPI()
    }

    private fun showCountries(list: List<Country>) {
        countryList.value = list
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSqlite(countryList: List<Country>) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            //*countryList.toTypedArray() verileri tek tek gonder
            val listLong = dao.insertAll(*countryList.toTypedArray())
            var i = 0

            while (i < listLong.size) {
                countryList[i].uuid = listLong[i].toInt()
                i++
            }
            showCountries(countryList)
        }
        customSharedPrefence.saveTime(System.nanoTime())
    }

    private fun getDataFromRoom() {
        launch {
            val countryList = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countryList)
            Toast.makeText(getApplication(), "Room Api", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

}