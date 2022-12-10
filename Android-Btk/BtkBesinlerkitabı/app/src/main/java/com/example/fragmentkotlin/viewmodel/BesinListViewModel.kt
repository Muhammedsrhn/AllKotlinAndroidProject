package com.example.fragmentkotlin.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fragmentkotlin.model.Besin
import com.example.fragmentkotlin.servis.BesinApiService
import com.example.fragmentkotlin.servis.BesinDatabase
import com.example.fragmentkotlin.util.PrivateSharedPrefences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListViewModel(application: Application) : BaseViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val besinErrorMessage = MutableLiveData<Boolean>()
    val besinLoading = MutableLiveData<Boolean>()

    // minutes to nanotime
    private var guncellemeZamani = 10 * 60 * 1000 * 1000 * 1000L

    private val besinApiServis = BesinApiService()
    private val disposable = CompositeDisposable()
    private val ozelSharedPreferences = PrivateSharedPrefences(getApplication())

    fun refreshData() {

        val kaydedilmeZamani = ozelSharedPreferences.getTime()
        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
            //Sqlite'tan çek
            verileriSQLitetanAl()
        } else {
            verileriInternettenAl()
        }


    }

    fun refreshFromInternet(){
        verileriInternettenAl()
    }

    private fun verileriSQLitetanAl(){
        besinLoading.value = true

        launch {

            val besinListesi = BesinDatabase(getApplication()).besinDao().getAllBesin()
            besinleriGoster(besinListesi)
            Toast.makeText(getApplication(),"Besinleri Room'dan Aldık",Toast.LENGTH_LONG).show()

        }

    }

    private fun verileriInternettenAl(){
        besinLoading.value = true

        disposable.add(
            besinApiServis.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>() {
                    override fun onSuccess(t: List<Besin>) {
                        //Başarılı olursa
                        sqliteSakla(t)
                        besinleriGoster(t)
                        Toast.makeText(getApplication(),"Besinleri Internet'ten Aldık",Toast.LENGTH_LONG).show()

                    }

                    override fun onError(e: Throwable) {
                        //Hata alırsak
                        besinErrorMessage.value = true
                        besinLoading.value = false
                        e.printStackTrace()
                    }

                })

        )

    }

    private fun besinleriGoster(besinlerListesi : List<Besin>){
        besinler.value = besinlerListesi
        besinErrorMessage.value = false
        besinLoading.value = false
    }

    private fun sqliteSakla(besinListesi: List<Besin>){

        launch {

            val dao = BesinDatabase(getApplication()).besinDao()
            dao.deleteAllBesin()
            val uuidListesi = dao.insertAll(*besinListesi.toTypedArray())
            var i = 0
            while (i < besinListesi.size){
                besinListesi[i].uuid = uuidListesi[i].toInt()
                i = i + 1
            }
            besinleriGoster(besinListesi)
        }

        ozelSharedPreferences.saveTime(System.nanoTime())

    }

}