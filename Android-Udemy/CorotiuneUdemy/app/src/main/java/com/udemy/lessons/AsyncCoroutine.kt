package com.udemy.lessons

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    var username = ""
    var userage = 0
    runBlocking {

        val downloadedName = async {
            downloadName()
        }

        val downloadedAge = async {
            downloadAge()
        }

        username = downloadedName.await()
        userage = downloadedAge.await()

        println("${username} , ${userage}")

    }

}

suspend fun downloadName(): String {
    delay(2000)
    val username = "Muhammed"
    println("Username Downloaded");
    return username
}

suspend fun downloadAge(): Int {
    delay(4000)
    val age = 21
    println("UserAge Downloaded");
    return age
}