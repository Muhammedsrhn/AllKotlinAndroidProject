package com.udemy.lessons

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    //Dispatcher
    //Dispatchers.Default -> CPU Intensive -> Yoğun CPU İşleri
    //Dispatchers.IO -> Input / Output -> Networking İşleri
    //Dispatchers.Main -> UI -> Kullanıcını Göreceği  İşlemler
    //Dispatchers.Unconfined -> Inherited -> Kalıtım Alan Dispatcher

    runBlocking {
        launch(Dispatchers.Main) {
            println("Main Thread : ${Thread.currentThread().name}")
        }
        launch(Dispatchers.IO) {
            println("IO Thread : ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default) {
            println("Default Thread : ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Unconfined) {
            println("Unconfined Thread : ${Thread.currentThread().name}")
        }
    }

}