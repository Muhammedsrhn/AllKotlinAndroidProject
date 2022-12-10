package com.udemy.brodcastreceiver.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application):AndroidViewModel(application),CoroutineScope {

    //job acıyoruz
    private val job = Job()
    override val coroutineContext: CoroutineContext
        // job + Dispatchers.Main  -> İşinini bu threadde yap sonra main threade gec
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        //job tamamladığında job'u iptal et
        job.cancel()
    }
}