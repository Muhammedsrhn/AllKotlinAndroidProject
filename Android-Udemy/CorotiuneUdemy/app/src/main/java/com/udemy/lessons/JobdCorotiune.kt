package com.udemy.lessons

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main(){
    //Job

    runBlocking {
        val myJob = launch {
            delay(4000)
            println("job")
            val myJob2 = launch {
                println("job 2")
            }
        }
        myJob.invokeOnCompletion {
            println("done")
        }

        myJob.cancel()
    }

}