package com.udemy.lessons

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        println("run block")
        MyFunc()
    }
}

suspend fun MyFunc() {
    coroutineScope {
        delay(5000)
        println("suspend function")
    }
}